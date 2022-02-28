package cn.itbk.smallbox.Views.StackLabel;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itbk.smallbox.R;
import cn.itbk.smallbox.Views.StackLabel.interfaces.OnLabelClickListener;

public class StackLabel extends ViewGroup {

    private int textColor = 0;
    private int textSize = 0;
    private int paddingVertical = 0;
    private int paddingHorizontal = 0;
    private int itemMargin = 0;
    private int itemMarginVertical = 0;
    private int itemMarginHorizontal = 0;
    private int maxLines = 0;
    private boolean deleteButton = false;

    private int deleteButtonImage = -1;
    private int labelBackground = -1;

    private boolean selectMode = false;
    private int selectBackground = -1;
    private int selectTextColor = -1;
    private int maxSelectNum = 0;
    private int minSelectNum = 0;

    private OnLabelClickListener onLabelClickListener;
    private Context context;
    private List<String> labels;

    private String loadLabelsArray;

    public StackLabel(Context context) {
        super(context);
        this.context = context;
    }

    public StackLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadAttrs(context, attrs);
    }

    public StackLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        loadAttrs(context, attrs);
    }

    private void loadAttrs(Context context, AttributeSet attrs) {
        try {
            //默认值
            textColor = Color.argb(230, 0, 0, 0);
            textSize = dp2px(12);
            paddingVertical = dp2px(8);
            paddingHorizontal = dp2px(12);
            itemMargin = dp2px(4);
            deleteButton = false;

            //加载值
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StackLabel);
            textColor = typedArray.getColor(R.styleable.StackLabel_textColor, textColor);
            textSize = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_textSize, textSize);
            paddingVertical = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_paddingVertical, paddingVertical);
            paddingHorizontal = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_paddingHorizontal, paddingHorizontal);
            itemMargin = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_itemMargin, itemMargin);
            itemMarginVertical = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_itemMarginVertical, itemMarginVertical);
            itemMarginHorizontal = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_itemMarginHorizontal, itemMarginHorizontal);
            deleteButton = typedArray.getBoolean(R.styleable.StackLabel_deleteButton, deleteButton);

            deleteButtonImage = typedArray.getResourceId(R.styleable.StackLabel_deleteButtonImage, deleteButtonImage);
            labelBackground = typedArray.getResourceId(R.styleable.StackLabel_labelBackground, labelBackground);

            selectMode = typedArray.getBoolean(R.styleable.StackLabel_selectMode, selectMode);
            selectBackground = typedArray.getResourceId(R.styleable.StackLabel_selectBackground, selectBackground);
            selectTextColor = typedArray.getColor(R.styleable.StackLabel_selectTextColor, selectTextColor);
            maxSelectNum = typedArray.getInt(R.styleable.StackLabel_maxSelectNum, maxSelectNum);
            minSelectNum = typedArray.getInt(R.styleable.StackLabel_minSelectNum, minSelectNum);
            maxLines = typedArray.getInt(R.styleable.StackLabel_maxLines, minSelectNum);
            if (minSelectNum > maxSelectNum && maxSelectNum != 0) minSelectNum = 0;

            loadLabelsArray = typedArray.getString(R.styleable.StackLabel_labels);

            if (selectBackground == -1) selectBackground = R.drawable.rect_label_bkg_select_normal;
            if (labelBackground == -1) labelBackground = R.drawable.rect_normal_label_button;
            typedArray.recycle();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (loadLabelsArray != null && !loadLabelsArray.isEmpty()) {
            if (loadLabelsArray.contains(",")) {
                setLabels(loadLabelsArray.split(","));
            } else {
                addLabel(loadLabelsArray);
            }
        }
    }

    private int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    private float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    private List<View> items;
    private int newHeight = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        refreshViews();

        setMeasuredDimension(getMeasuredWidth(), newHeight);//设置宽高
    }

    private void refreshViews() {
        int maxWidth = getMeasuredWidth();


        if (labels != null && !labels.isEmpty()) {
            newHeight = 0;
            if (items != null && !items.isEmpty()) {
                int l = 0, t = 0, r = 0, b = 0, lines = 0;

                for (int i = 0; i < items.size(); i++) {
                    View item = items.get(i);

                    int mWidth = View.MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);           //AT_MOST：先按照最大宽度计算，如果小于则按实际值，如果大于，按最大宽度
                    int mHeight = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);         //UNSPECIFIED：不确定，根据实际情况计算
                    item.measure(mWidth, mHeight);

                    int childWidth = item.getMeasuredWidth();
                    int childHeight = item.getMeasuredHeight();

                    if ((l + childWidth) > maxWidth) {
                        l = 0;
                        t = t + childHeight;
                        lines++;
                    }

                    if (maxLines > 0 && lines >= maxLines) {
                        item.setVisibility(GONE);
                    } else {
                        r = l + childWidth;

                        if (childWidth > maxWidth) {
                            r = maxWidth;
                        }

                        b = t + childHeight;

                        item.layout(l, t, r, b);

                        l = l + childWidth;

                        newHeight = t + childHeight;
                    }
                }
            }
        }
    }

    public List<String> getLabels() {
        return labels;
    }

    public StackLabel setLabels(List<String> l) {
        labels = l;

        reloadViews();
        return this;
    }

    public boolean remove(int index) {
        if (labels == null) {
            return false;
        }
        labels.remove(index);
        reloadViews();
        return true;
    }

    public boolean remove(String label) {
        if (labels == null) {
            return false;
        }
        boolean flag = labels.remove(label);
        reloadViews();
        return flag;
    }

    public StackLabel setLabels(String[] arrays) {
        labels = new ArrayList<>();
        for (String s : arrays) {
            labels.add(s);
        }

        reloadViews();
        return this;
    }

    public boolean isHave(String label) {
        if (labels == null) {
            return false;
        }
        return labels.contains(label);
    }

    public int count() {
        if (labels == null) {
            return 0;
        } else {
            return labels.size();
        }
    }

    public void reloadViews() {
        removeAllViews();
        items = new ArrayList<>();
        if (labels != null && !labels.isEmpty()) {

            for (int i = 0; i < labels.size(); i++) {
                View item = LayoutInflater.from(context).inflate(R.layout.view_label, null, false);

                addView(item);
                items.add(item);
            }

            initItem();
        }
    }

    public StackLabel addLabel(String label) {
        if (labels == null) labels = new ArrayList<>();
        labels.add(label);

        reloadViews();
        return this;
    }

    private List<Integer> selectIndexs = new ArrayList<>();

    private void initItem() {
        if (labels.size() != 0) {
            selectIndexs = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                View item = items.get(i);

                String s = labels.get(i);
                LinearLayout boxLabel = item.findViewById(R.id.box_label);
                TextView txtLabel = item.findViewById(R.id.txt_label);
                ImageView imgDelete = item.findViewById(R.id.img_delete);

                txtLabel.setText(s);
                txtLabel.setTextColor(textColor);
                txtLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                boxLabel.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) boxLabel.getLayoutParams();
                if (itemMarginHorizontal == 0 && itemMarginVertical == 0) {
                    p.setMargins(itemMargin, itemMargin, itemMargin, itemMargin);
                } else {
                    p.setMargins(itemMarginHorizontal, itemMarginVertical, itemMarginHorizontal, itemMarginVertical);
                }
                boxLabel.requestLayout();

                if (deleteButton) {
                    imgDelete.setVisibility(VISIBLE);
                } else {
                    imgDelete.setVisibility(GONE);
                }
                if (deleteButtonImage != -1) imgDelete.setImageResource(deleteButtonImage);
                boxLabel.setBackgroundResource(labelBackground);

                final int index = i;
                boxLabel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectMode) {
                            for (View item : items) {
                                LinearLayout boxLabel = item.findViewById(R.id.box_label);
                                TextView txtLabel = item.findViewById(R.id.txt_label);
                                boxLabel.setBackgroundResource(labelBackground);

                                txtLabel.setTextColor(textColor);
                                txtLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            }
                            if (selectIndexs.contains(index)) {
                                if (selectIndexs.size() > minSelectNum) {
                                    int ind = 0;
                                    for (int i = 0; i < selectIndexs.size(); i++) {
                                        if (selectIndexs.get(i) == index) {
                                            ind = i;
                                            break;
                                        }
                                    }
                                    selectIndexs.remove(ind);
                                }
                            } else {
                                if (maxSelectNum == 1) selectIndexs.clear();
                                if (maxSelectNum <= 0 || (maxSelectNum > 0 && selectIndexs.size() < maxSelectNum)) {
                                    selectIndexs.add(index);
                                }
                            }
                            for (int index : selectIndexs) {
                                View item = items.get(index);
                                LinearLayout boxLabel = item.findViewById(R.id.box_label);
                                TextView txtLabel = item.findViewById(R.id.txt_label);
                                boxLabel.setBackgroundResource(selectBackground);
                                txtLabel.setTextColor(selectTextColor);
                            }
                        }
                        if (onLabelClickListener != null)
                            onLabelClickListener.onClick(index, v, labels.get(index));
                    }
                });

                if (whichIsSelected != null) {
                    for (String selectStr : whichIsSelected) {
                        if (s.equals(selectStr)) {
                            selectIndexs.add(index);
                            boxLabel.setBackgroundResource(selectBackground);
                            txtLabel.setTextColor(selectTextColor);
                        }
                    }
                }
            }
            whichIsSelected = null;
        }
    }

    public OnLabelClickListener getOnLabelClickListener() {
        return onLabelClickListener;
    }

    public StackLabel setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
        return this;
    }

    public boolean isDeleteButton() {
        return deleteButton;
    }

    public StackLabel setDeleteButton(boolean deleteButton) {
        this.deleteButton = deleteButton;
        initItem();
        return this;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public StackLabel setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
        setLabels(labels);
        return this;
    }

    private List<String> whichIsSelected;       //初始化已选择列表

    public StackLabel setSelectMode(boolean selectMode, List<String> whichIsSelected) {
        this.selectMode = selectMode;
        if (selectMode) {
            this.whichIsSelected = whichIsSelected;
        } else {
            whichIsSelected = null;
        }
        setLabels(labels);
        return this;
    }

    public int getSelectBackground() {
        return selectBackground;
    }

    public StackLabel setSelectBackground(int selectBackground) {
        this.selectBackground = selectBackground;
        setLabels(labels);
        return this;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public StackLabel setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public StackLabel setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        setLabels(labels);
        return this;
    }

    public List<Integer> getSelectIndexList() {
        return selectIndexs;
    }

    public int[] getSelectIndexArray() {
        int[] arrays = new int[selectIndexs.size()];
        for (int i = 0; i < selectIndexs.size(); i++) {
            arrays[i] = selectIndexs.get(i);
        }
        return arrays;
    }

    public int getTextColor() {
        return textColor;
    }

    public StackLabel setTextColor(int textColor) {
        this.textColor = textColor;
        setLabels(labels);
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public StackLabel setTextSize(int textSize) {
        this.textSize = textSize;
        setLabels(labels);
        return this;
    }

    public int getPaddingVertical() {
        return paddingVertical;
    }

    public StackLabel setPaddingVertical(int paddingVertical) {
        this.paddingVertical = paddingVertical;
        setLabels(labels);
        return this;
    }

    public int getPaddingHorizontal() {
        return paddingHorizontal;
    }

    public StackLabel setPaddingHorizontal(int paddingHorizontal) {
        this.paddingHorizontal = paddingHorizontal;
        setLabels(labels);
        return this;
    }

    public int getItemMargin() {
        return itemMargin;
    }

    public StackLabel setItemMargin(int itemMargin) {
        this.itemMargin = itemMargin;
        setLabels(labels);
        return this;
    }

    public int getItemMarginVertical() {
        return itemMarginVertical;
    }

    public StackLabel setItemMarginVertical(int itemMarginVertical) {
        this.itemMarginVertical = itemMarginVertical;
        setLabels(labels);
        return this;
    }

    public int getItemMarginHorizontal() {
        return itemMarginHorizontal;
    }

    public StackLabel setItemMarginHorizontal(int itemMarginHorizontal) {
        this.itemMarginHorizontal = itemMarginHorizontal;
        setLabels(labels);
        return this;
    }

    public int getMaxLines() {
        return maxLines;
    }

    public StackLabel setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        return this;
    }

    private void log(Object s) {
        Log.i(">>>", s.toString());
    }
}