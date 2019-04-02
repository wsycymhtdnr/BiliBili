package com.bilibili.lingxiao.play

import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bilibili.lingxiao.R
import com.bilibili.lingxiao.dagger.DaggerUiComponent
import com.bilibili.lingxiao.home.recommend.RecommendData
import com.camera.lingxiao.common.app.BaseFragment
import kotlinx.android.synthetic.main.fragment_introduce.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class IntroduceFragment :BaseFragment(){
    var mEndPageList = arrayListOf<EndPageData>()
    lateinit var endPageAdapter: EndPageAdapter
    override val contentLayoutId: Int
        get() = R.layout.fragment_introduce

    override fun initInject() {
        super.initInject()
        DaggerUiComponent.builder().build().inject(this)
    }
    override fun initWidget(root: View) {
        super.initWidget(root)
        var manager = GridLayoutManager(context,5)
        root.endpage_recycler.layoutManager = manager
        endPageAdapter = EndPageAdapter(R.layout.item_endpage,mEndPageList)
        root.endpage_recycler.adapter = endPageAdapter
        root.endpage_recycler.isNestedScrollingEnabled = false
    }

    data class EndPageData(val icon:Drawable,val message:String){

    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onGetVideoDetail(data: RecommendData) {
        var recommend = EndPageData(resources.getDrawable(R.drawable.ic_recommend),""+data.like)
        var dislike = EndPageData(resources.getDrawable(R.drawable.ic_dislike),"不喜欢")
        var coin = EndPageData(resources.getDrawable(R.drawable.ic_coin),""+data.coin)
        var collect = EndPageData(resources.getDrawable(R.drawable.ic_collect),""+data.reply)
        var share = EndPageData(resources.getDrawable(R.drawable.ic_share),""+data.share)
        endPageAdapter.addData(recommend)
        endPageAdapter.addData(dislike)
        endPageAdapter.addData(coin)
        endPageAdapter.addData(collect)
        endPageAdapter.addData(share)

    }
}