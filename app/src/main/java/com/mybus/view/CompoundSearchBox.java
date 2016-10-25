package com.mybus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.listener.CompoundSearchBoxListener;

/**
 * Created by ldimitroff on 02/06/16.
 */
public class CompoundSearchBox extends FrameLayout {

    private View mMainLayout;
    private TextView mToTextView;
    private TextView mFromTextView;
    private ImageView mDrawerToggle;
    private ImageView mFlipSearchBtn;
    private ImageView mSearchBtn;
    private String mFromAddress = null;
    private String mToAddress = null;
    private CompoundSearchBoxListener mListener;

    private OnClickListener mFromClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onFromClick();
            }
        }
    };
    private OnClickListener mToClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onToClick();
            }
        }
    };
    private OnClickListener mDrawerToggleClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setFromAddress(null);
            setToAddress(null);
            setSearchEnabled(false);
            if (mListener != null) {
                mListener.onBackArrowClick();
            }
        }
    };
    private OnClickListener mFlipSearchClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mFromAddress != null && mToAddress != null) {
                String aux = mFromTextView.getText().toString();
                mFromTextView.setText(mToTextView.getText());
                mToTextView.setText(aux);
                if (mListener != null) {
                    mListener.onFlipSearchClick();
                    mListener.onSearchButtonClick();
                }
            }
        }
    };
    private OnClickListener mSearchBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onSearchButtonClick();
            }
        }
    };


    public CompoundSearchBox(Context context) {
        super(context);
        initView();
    }

    public CompoundSearchBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CompoundSearchBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mMainLayout = inflate(getContext(), R.layout.activity_main_search_box, this);
        mFromTextView = (TextView) findViewById(R.id.fromTextView);
        mFromTextView.setOnClickListener(mFromClickListener);

        mToTextView = (TextView) findViewById(R.id.toTextView);
        mToTextView.setOnClickListener(mToClickListener);

        mDrawerToggle = (ImageView) findViewById(R.id.drawerToggle);
        mDrawerToggle.setOnClickListener(mDrawerToggleClickListener);

        mFlipSearchBtn = (ImageView) findViewById(R.id.flipSearch);
        mFlipSearchBtn.setOnClickListener(mFlipSearchClickListener);

        mSearchBtn = (ImageView) findViewById(R.id.searchBtn);
        mSearchBtn.setOnClickListener(mSearchBtnClickListener);

    }

    /**
     * @param resId
     * @return
     */
    private String getResString(int resId) {
        return getContext().getString(resId);
    }

    /**
     * @param listener
     */
    public void setListener(CompoundSearchBoxListener listener) {
        this.mListener = listener;
    }

    /**
     * @param visible
     */
    public void setVisible(boolean visible) {
        setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * * Show or hide the SearchBar
     *
     * @param visible
     * @param animate
     */
    public void setVisible(boolean visible, boolean animate) {
        setVisible(visible);
        if (animate) {
            mMainLayout.startAnimation(AnimationUtils.loadAnimation(getContext(),
                    visible ? R.anim.up_bottom : R.anim.bottom_up));
        }
    }

    /**
     * @param text
     */
    public void setFromAddress(String text) {
        mFromAddress = text;
        if (text == null) {
            text = getResString(R.string.from_hint);
        }
        mFromTextView.setText(text);
    }

    /**
     * @param text
     */
    public void setToAddress(String text) {
        mToAddress = text;
        if (text == null) {
            text = getResString(R.string.to_hint);
        }
        mToTextView.setText(text);
    }

    /**
     * @return
     */
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    /**
     * Enables or disables the search button
     *
     * @param enabled
     */
    public void setSearchEnabled(boolean enabled) {
        mSearchBtn.setEnabled(enabled);
    }

    public String getToAddress() {
        return mToAddress;
    }

    public String getFromAddress() {
        return mFromAddress;
    }
}
