package com.example.wolver.calismacetveli.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class RecTouchCallBack extends ItemTouchHelper.Callback {

    //1-"extends ItemTouchHelper.Callback" ve Alt+Enter ile metodları impleme et
    //2-"getMovementFlags" metodunda, "return makeMovementFlags(0, ItemTouchHelper.END);" yaparak sadece swipe yapacağoımızı belirttik.
    //3-"isLongPressDragEnabled" ve isItemViewSwipeEnabled metodlarını oluşturduk.
    //4-"isLongPressDragEnabled" için "return false", "isItemViewSwipeEnabled" için "return true" dönderdik.

    //5-Sınıflar arasında iletişim için; SwipeListener "interface" bir sınıf oluşturduk.
    //6-SwipeListener sınıfında "void swipeIleSil(int position);" metodu oluşturuldu.

    //7-Bu sınıfta(RecTouchCallBack) SiwipeListener" nesnesi tanımlayarak, constructer oluşturduk. (new demeyeceksin.)

    //8-"RecAdapter" sınıfına "SwipeListener Sınıfını" impleme ettik. Alt+Enter ile "swipeIleSil" metodu oluşturuldu.

    //9-Bu sınıfta(RecTouchCallBack) onSwiped metodunu aşağıdaki gibi (10) dolduruyoruz.
    //10-mSwipeListener.swipeIleSil(viewHolder.getAdapterPosition());

    //11-"MainActivity" de aşağıdaki kodlar ile tanımlaması yapıldı.
    //    RecTouchCallBack mRecTouchCallBack = new RecTouchCallBack(mRecAdapter);
    //    ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mRecTouchCallBack);
    //    mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    //12-"RecAdapter" de en üstte "Context" değişkeni tanımladık.
    //      private ContentResolver contentResolver;

    //13-public RecAdapter(Context context, ArrayList<Liste> liste) metodunun içerisine alttaki kod yazılarak. getContentResolver a ulaştık.
    //      contentResolver = context.getContentResolver();
    //
    //14-Daha sonra bu sınıftaki oluşturduğumuz, swipeIleSil metodu dolduruldu.


    SwipeListener mSwipeListener;

    public RecTouchCallBack(SwipeListener mSwipeListener) {
        this.mSwipeListener = mSwipeListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if (direction == ItemTouchHelper.END) {
            mSwipeListener.swipeIleSil(viewHolder.getAdapterPosition());
        }
        if (direction == ItemTouchHelper.START) {
            mSwipeListener.swipeIleYoket(viewHolder.getAdapterPosition());
        }
    }
}
