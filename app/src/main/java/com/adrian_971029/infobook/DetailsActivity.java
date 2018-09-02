package com.adrian_971029.infobook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.infobook.data.ContentProviderAccess;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.adrian_971029.infobook.utils.MyFrameLayout;
import com.adrian_971029.infobook.utils.MyScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {

    private static final String VOLUME_INFO = "volume_info";
    private static final float PARALLAX_FACTOR = 1.25f;

    @BindView(R.id.img_book_details)
    ImageView mImagebook;
    @BindView(R.id.book_title_details)
    TextView mTextViewTitle;
    @BindView(R.id.book_author_details)
    TextView mTextViewAuthor;
    @BindView(R.id.content_descriptions_details)
    TextView mTextViewDescription;
    @BindView(R.id.tv_publisher_details)
    TextView mTextViewPublisher;
    @BindView(R.id.tv_publisherDate_details)
    TextView mTextViewPublisherDate;
    @BindView(R.id.tv_language_details)
    TextView mTextViewLanguage;
    @BindView(R.id.tv_pageCount_details)
    TextView mTextViewPageCount;
    @BindView(R.id.tv_categories_details)
    TextView mTextViewCategories;
    @BindView(R.id.fab_share_details)
    FloatingActionButton mFab;
    @BindView(R.id.my_scrollview)
    MyScrollView myScrollView;
    @BindView(R.id.my_frame_layout)
    MyFrameLayout myFrameLayout;
    @BindView(R.id.img_book_container)
    View mImageViewContainer;
    @BindView(R.id.action_up)
    View mUpButton;
    @BindView(R.id.up_container)
    View mUpButtonContainer;
    @BindView(R.id.rtb_averageRating_details)
    RatingBar mRatingBarAverageRating;
    @BindView(R.id.btn_add_favorites)
    TextView mFavorites;
    @BindView(R.id.card_view_button)
    CardView mCardViewFavorites;

    private VolumeInfo volumeInfo;
    private int mTop;
    private int scrollY;
    private int statusBarOpacity;
    private ColorDrawable mStatusBarColorDrawable;
    private int mMutedColor = 0x009688;
    private int mSelectedItemUpButtonFloor = Integer.MAX_VALUE;
    private boolean controlFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        volumeInfo = new VolumeInfo();
        volumeInfo = getIntent().getExtras().getParcelable(VOLUME_INFO);

        statusBarOpacity = getResources().getDimensionPixelSize(
                R.dimen.cardview_compat_inset_shadow);

        myFrameLayout.setOnInsetsCallback(new MyFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                mTop = insets.top;
            }
        });

        myScrollView.setCallbacks(new MyScrollView.Callbacks() {
            @Override
            public void onScrollChanged() {
                scrollY = myScrollView.getScrollY();
                mImageViewContainer.setTranslationY((int) (scrollY - scrollY / PARALLAX_FACTOR));
                updateStatusBar();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mUpButtonContainer.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    view.onApplyWindowInsets(windowInsets);
                    mTop = windowInsets.getSystemWindowInsetTop();
                    mUpButtonContainer.setTranslationY(mTop);
                    updateUpButtonPosition();
                    return windowInsets;
                }
            });
        }

        mStatusBarColorDrawable = new ColorDrawable(0);
        criarLayout();
    }

    @OnClick(R.id.action_up)
    void onClickActionUp() {
        finish();
    }
    @OnClick(R.id.fab_share_details)
    void onClickShareFab() {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType(getString(R.string.txt_html))
                .setText(volumeInfo.getInfoLink())
                .getIntent(),getString(R.string.action_share)));
    }

    @OnClick(R.id.card_view_button)
    void onClickBtnFavorites() {
        if(mFavorites.getText().toString().equals(getResources().getString(R.string.lbl_add_a_favoritos))) {
            inserirCadastroBanco();
            mFavorites.setText(R.string.lbl_favorito);
            guardandoFavorito(true);
            mCardViewFavorites.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast.makeText(this, R.string.lbl_save_favorites,Toast.LENGTH_SHORT).show();
        } else {
            if (!deletarCadastroBanco(String.valueOf(convertStringidToLong(volumeInfo.getTitle() + volumeInfo.getPublisher())))) return;
            mFavorites.setText(R.string.lbl_add_a_favoritos);
            guardandoFavorito(false);
            mCardViewFavorites.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            Toast.makeText(this, R.string.lbl_delete_favorites,Toast.LENGTH_SHORT).show();
        }
    }

    private void criarLayout() {
        if(volumeInfo != null) {
            if(volumeInfo.getImageLinks().getExtraLarge() != null) {
                Picasso.with(this).load(volumeInfo.getImageLinks().getExtraLarge()).into(mImagebook);
            } else if(volumeInfo.getImageLinks().getLarge() != null) {
                Picasso.with(this).load(volumeInfo.getImageLinks().getLarge()).into(mImagebook);
            } else if(volumeInfo.getImageLinks().getMedium() != null) {
                Picasso.with(this).load(volumeInfo.getImageLinks().getMedium()).into(mImagebook);
            } else if(volumeInfo.getImageLinks().getSmall() != null) {
                Picasso.with(this).load(volumeInfo.getImageLinks().getSmall()).into(mImagebook);
            } else if(volumeInfo.getImageLinks().getThumbnail() != null) {
                Picasso.with(this).load(volumeInfo.getImageLinks().getThumbnail()).into(mImagebook);
            } else {
                Picasso.with(this).load(volumeInfo.getImageLinks().getSmallThumbnail()).into(mImagebook);
            }
            mTextViewTitle.setText(volumeInfo.getTitle());
            mTextViewAuthor.setText(allAuthor(volumeInfo.getAuthors()));
            mTextViewPublisher.setText(volumeInfo.getPublisher());
            mTextViewPublisherDate.setText(volumeInfo.getPublishedDate());
            mTextViewLanguage.setText(defineLanguage(volumeInfo.getLanguage()));
            mTextViewPageCount.setText("" + volumeInfo.getPageCount());
            mTextViewCategories.setText(allCategories(volumeInfo.getCategories()));
            mRatingBarAverageRating.setRating((float)volumeInfo.getAverageRating());
            mRatingBarAverageRating.setIsIndicator(true);
            mTextViewDescription.setText(volumeInfo.getDescription());
        }
    }

    private String allAuthor(ArrayList<String> mAuthors) {
        String result = "";
        if (mAuthors != null) {
            if (mAuthors.size() == 0) {
                return result;
            } else if (mAuthors.size() == 1) {
                result = getApplicationContext().getResources().getString(R.string.lbl_by) + " ";
                result += mAuthors.get(0);
                return result;
            } else {
                result = getApplicationContext().getResources().getString(R.string.lbl_by) + " ";
                for (int i = 0; i < mAuthors.size(); i++) {
                    if (i == mAuthors.size()-1) {
                        result += mAuthors.get(i);
                    } else {
                        result += mAuthors.get(i) + " , ";
                    }
                }
                return result;
            }
        } else {
            return result;
        }
    }

    private String allCategories(ArrayList<String> mCategories) {
        String result = "";
        if (mCategories != null) {
            if (mCategories.size() == 0) {
                return result;
            } else if (mCategories.size() == 1) {
                result += mCategories.get(0);
                return result;
            } else {
                for (int i = 0; i < mCategories.size(); i++) {
                    if (i == mCategories.size()-1) {
                        result += mCategories.get(i);
                    } else {
                        result += mCategories.get(i) + " , ";
                    }
                }
                return result;
            }
        } else {
            return result;
        }
    }

    private String defineLanguage(String language) {
        String result = language;
        switch (language) {
            case "en":
                result = getString(R.string.lbl_ingles);
                return result;
            case "pt":
                result = getString(R.string.lbl_portugues);
                return result;
            case "es":
                result = getString(R.string.lbl_espanhol);
                return result;
            case "de":
                result = getString(R.string.lbl_alemao);
                return result;
            case "fr":
                result = getString(R.string.lbl_frances);
                return result;
            default:
                return result;
        }
    }

    private void updateStatusBar() {
        int color = 0;
        if (mImagebook != null && mTop != 0 && scrollY > 0) {
            float f = progress(scrollY,
                    statusBarOpacity - mTop * 3,
                    statusBarOpacity - mTop);
            color = Color.argb((int) (255 * f),
                    (int) (Color.red(mMutedColor) * 0.9),
                    (int) (Color.green(mMutedColor) * 0.9),
                    (int) (Color.blue(mMutedColor) * 0.9));
        }
        mStatusBarColorDrawable.setColor(color);
        myFrameLayout.setInsetBackground(mStatusBarColorDrawable);
    }

    static float progress(float v, float min, float max) {
        return constrain((v - min) / (max - min), 0, 1);
    }

    static float constrain(float val, float min, float max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    private void updateUpButtonPosition() {
        int upButtonNormalBottom = mTop + mUpButton.getHeight();
        mUpButton.setTranslationY(Math.min(mSelectedItemUpButtonFloor - upButtonNormalBottom, 0));
    }

    private void inserirCadastroBanco() {
        try {
            volumeInfo.setId(convertStringidToLong(volumeInfo.getTitle() + volumeInfo.getPublisher()));
            ContentProviderAccess.inserirContentProvider(this,volumeInfo);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean deletarCadastroBanco(String id){
        boolean sucesso = false;
        try {
            sucesso = ContentProviderAccess.deletar(volumeInfo,id,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucesso;
    }

    private void guardandoFavorito(boolean favorito){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(String.valueOf(volumeInfo.getTitle()),favorito);
        editor.apply();
    }

    private void controlFavorito() {
        if(sharedPrefs != null) {
            if (volumeInfo != null) {
                controlFavorito = sharedPrefs.getBoolean(String.valueOf(volumeInfo.getTitle()),false);
            } else {
                controlFavorito = false;
            }
        }

        if(controlFavorito) {
            mFavorites.setText(R.string.lbl_favorito);
            mCardViewFavorites.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            mFavorites.setText(R.string.lbl_add_a_favoritos);
            mCardViewFavorites.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private long convertStringidToLong(String id) {
        int numberId = 0;
        long result = 0;
        char[] mchar = new char[id.length()];
        mchar = id.toCharArray();
        for (int i=0;i<id.length();i++) {
            numberId += mchar[i];
        }
        result = (long)numberId;
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        controlFavorito();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controlFavorito();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            volumeInfo = intent.getExtras().getParcelable(VOLUME_INFO);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getIntent().removeExtra(VOLUME_INFO);
    }
}
