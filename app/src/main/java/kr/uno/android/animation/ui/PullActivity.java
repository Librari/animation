package kr.uno.android.animation.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.PullAdapter;
import kr.uno.android.animation.listener.RecyclerListenerAdapter;
import kr.uno.android.animation.ui.widget.BaseRecyclerView;

public class PullActivity extends ActionBarActivity {

    @InjectView(R.id.recycler_pull) BaseRecyclerView mRecyclerPull;

    private RecyclerView.LayoutManager mLayoutManager;
    private PullAdapter mAdapterPull;
    private List<String> mItemList = Arrays.asList(("한국은행이 올해 국내총생산(GDP) 성장률 전망치를 3%대 초반까지 끌어내리면서 우리 경제의 저성장이 고착화되는 것 아니냐는 우려가 증폭되고 있다. " +
            "특히 이대로라면 일본의 '잃어버린 20년'의 전철을 밟을 수도 있다는 경고등이 곳곳에서 켜지고 있다. 경기를 살리기 위한 정부의 추가 대책, 노동시장 구조개선 등 정부가 추진하는 구조개혁, " +
            "국회에 계류 중인 경제활성화법의 신속한 처리 등이 이어져야 간신히 살아난 경기불씨를 살릴 수 있다는 것이다. 이주열 한은 총재는 9일 불가피할 경우 정부의 추경예산 필요성도 제기했다. " +
            "이 총재는 이날 금융통화위원회 통화정책방향회의를 열고 올해 GDP 성장률 전망치를 기존 3.4%에서 0.3%포인트 내린 3.1%로 수정했다. 연간 소비자물가 상승률도 1.9%에서 0.9%로 내려잡았다. 다만 2·4분기부터 경제가 회복될 것으로 보고 있는 점은 긍정적이다. 기준금리는 일단 1.75%를 유지키로 했다." +
            "올해 성장률 전망치를 대거 하향 조정한 것은 지난해 말 대규모 세수부족이 경제성장의 발목을 잡았기 때문이다. 11조원의 세수펑크가 발생하면서 지난해 4·4분기 GDP 성장률은 당초 예상치인 1%대에 크게 못 미친 전기 대비 0.3%를 기록했다. 0.4%였던 속보치에도 미달했다. " +
            "직전 해 마지막 분기 성장률은 이듬해 첫 분기 성장률에도 직결된다. 실제로 올해 1·4분기 실적치 역시 세수부족 여파를 비켜가지 못하고 예상보다 부진한 모습을 보였다. " +
            "이 총재도 이날 기자간담회에서 지난해 4·4분기 성장률이 0.3%에 그친 것은 결정적으로 세수부족 때문이라면서 세수부족이 생기면 당해 연도뿐 아니라 다음 해에도 크게 영향을 주게 돼있다고 설명했다." +
            "이 총재는 올해도 최소 6조원의 세수부족이 발생할 것으로 내다보고 추경 필요성을 강하게 시사했다. 그는 예정된 세출은 추경을 통해서라도 집행해야 하는 것이라면서 재정건전성을 양보해서라도 성장잠재력 회복을 위해 재정이 일정한 역할을 해줘야 한다고 강조했다." +
            "이 총재는 그러나 한국 경제가 미약하지만 회복세를 띠고 있다면서 디플레이션 우려에 대해선 다시 한번 일축했다. 이 총재는 최근 경제지표들이 반등 기미를 보이고 있다면서 추세적으로 갈지는 지켜봐야 하겠지만 지난해 하반기 두 차례 금리인하 효과가 영향을 주고 있다고 본다고 말했다.").split("\\."));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        ButterKnife.inject(this);

        initView();
        initControl();
        initValue();
    }

    private void initView() {
        mAdapterPull = new PullAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerPull.setLayoutManager(mLayoutManager);
        mRecyclerPull.setAdapter(mAdapterPull);
    }

    private void initControl() {

        mRecyclerPull.setOnRecyclerListener(new RecyclerListenerAdapter() {
            @Override
            public boolean isOffsetEnable() {
                return mAdapterPull.isExpanded();
            }

            @Override
            public void onScrollOffset(int offset, boolean isMove) {
                super.onScrollOffset(offset, isMove);
                if (isMove) mAdapterPull.setHeaderHeightOffset(offset);
                else mAdapterPull.foldingHeader();
            }
        });
    }

    private void initValue() {
        mAdapterPull.setHeader(R.drawable.sample1);
        mAdapterPull.setItemList(mItemList);
    }

}
