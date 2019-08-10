private void initSlider() {

     SlideShowViewPagerAdapter   slideShowViewPagerAdapter = new SlideShowViewPagerAdapter(getActivity(), Constants.masterResponse.getBanner());
        slider.setAdapter(slideShowViewPagerAdapter);
        slider.setCurrentItem(0);
        slider.startAutoScroll(3000);
        slider.setInterval(2000);

        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isAdded()) {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_item_dot));
                    }
                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_item_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUpIndicator();
    }


    private void setUpIndicator() {
        dotsCount = slideShowViewPagerAdapter.getCount();
        try {
            if (dotsCount > 0) {
                dots = new ImageView[dotsCount];
                for (int i = 0; i < dotsCount; i++) {
                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_item_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins(4, 0, 4, 0);
                    slideshowCountDots.addView(dots[i], params);
                }

                dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_item_dot));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    
    
    
    
    public class SlideShowViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<AllMasterResponse.Banner> homeBanners;
    private int viewType;
    private OnBannerClickListener onBannerClickListener;
    public EventListener mEventListener;

    public SlideShowViewPagerAdapter(Context mContext, List<AllMasterResponse.Banner> homeBanners) {
        this.mContext = mContext;
        this.homeBanners = homeBanners;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }


    @Override
    public int getCount() {
        return homeBanners.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view;
//        if (viewType == 0)
//        {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_slideshow, container, false);
//        }
//        else {
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_product_slideshow, container, false);
//        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner);
//        String ImgURL = new URLs().MAIN_URL+"/"+homeBanners.get(position).file;
//        Picasso.with(mContext).load(ImgURL).into(imageView);

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_big_placeholder);
        options.error(R.drawable.ic_big_placeholder);

        Glide.with(mContext)
                .applyDefaultRequestOptions(options)
                .load(homeBanners.get(position).getFile_Url()).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
        //homeBanners.clear();
        container.refreshDrawableState();
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnBannerClickListener {
        public void onClick(String link);
    }

    public EventListener getEventListener() {
        return mEventListener;
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }


    public interface EventListener {
        void onClick(String position);
    }

}
