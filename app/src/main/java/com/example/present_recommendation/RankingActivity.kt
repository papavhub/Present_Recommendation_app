package com.example.present_recommendation

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.ranking.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class RankingActivity : AppCompatActivity() {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef : DatabaseReference = database.getReference()

    data class User(
        var list1 : MutableList<String>,
        var list2 : MutableList<String>
    )


    fun writeNewUser(userId : String, uid : String, email : String, txtName : String, list1 : MutableList<String>, list2 : MutableList<String>){
        var user = User(list1, list2)
        val txt : String = txtName.replace(".", "")
        myRef.child("users").child(userId).child(txt).setValue(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking)

        var intent = intent
        var myarray : Array<String> = intent.getStringArrayExtra("recommend_list_top_10") as Array<String>
        var topten : Array<String> = intent.getStringArrayExtra("strong_recommend_list_top_10") as Array<String>
        var txtFileName : String? = intent.getStringExtra("txtFileName")

        var adapter1 : ArrayAdapter<String> = ArrayAdapter(this, R.layout.fontstyleblack, myarray)
        var adapter2 : ArrayAdapter<String> = ArrayAdapter(this, R.layout.fontstyleblack, topten)

        listView2.adapter = adapter1
        listView1.adapter = adapter2

        listView1.setOnTouchListener(){ view: View, motionEvent: MotionEvent ->
            listView1.requestDisallowInterceptTouchEvent(true)
            false
        }

        listView2.setOnTouchListener(){ view: View, motionEvent: MotionEvent ->
            listView1.requestDisallowInterceptTouchEvent(true)
            false
        }


        /*listView2.setOnItemClickListener { parent, view, position, id ->
            var word : Sring = myarray[position].toString()

            var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
            var uri = Uri.parse(url)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }*/

        listView2.setOnItemClickListener { parent, view, position, id ->
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("검색하기") //제목
            dlg.setMessage("오프라인 매장과 온라인 매장을 검색할 수 있어요") // 메시지
            dlg.setPositiveButton("오프라인", DialogInterface.OnClickListener { dialog, which ->
                var execepted_gift_list: List<String> = arrayListOf(
                        // 자동차 용품
                        "스마트폰 거치대", "방향제", "카시트 커버", "카시트", "세차", "차량관리용품", "안전삼각대", "차량용소화기", "주차번호판", "차량용 청소기", "차량용 미니 청소기", "차량용", "불스원", "더존", "휘링",

                        //패션의류 / 잡화
                        "티셔츠", "맨투맨", "후드티", "블라우스", "셔츠", "원피스", "정장", "바지", "레깅스", "스커트", "치마", "트레이닝복", "후드집업", "니트", "조끼", "아우터", "잠옷", "커플룩", "패밀리룩", "스포츠의류", "임산부의류", "한복", "수의", "신발", "가방", "슬랙스", "에코백", "캔버스화", "양말", "반팔",

                        //뷰티
                        "스킨", "로션", "에센스", "세럼", "앰플", "미스트", "오일", "크림", "올인원", "마스크", "팩", "선케어", "태닝", "클렌징폼", "클렌징오일", "프라이머", "아이리무버", "클렌징티슈", "필링", "아이라이너", "아이섀도", "마스카라", "아이브로우", "틴트", "립글로스", "립스틱", "파운데이션", "컨실러", "메이크업픽서", "블러셔",

                        //육아
                        "배냇저고리", "수유복", "젖병건조대", "롬퍼", "스패츠", "보행기", "걸음마신발", "신생아모자", "손싸개", "발싸개", "겉싸개", "속싸개", "가제수건", "턱받이", "스카프빕", "배가리개", "출산선물", "돌반지", "미아방지가방", "미아방지주얼리", "기저귀", "일회용기저귀", "천기저귀", "배변훈련팬티", "기저귀크림", "기저귀파우더", "분유", "이유식", "젖병", "젖병세척",
                        //식품
                        "과일", "견과", "채소", "쌀", "잡곡", "계란", "음료", "커피", "원두", "차", "과자", "초콜릿", "시리얼", "씨리얼", "드레싱", "오일", "유제품", "유기농", "프로틴", "대용식", "마라탕",

                        //주방용품
                        "냄비", "프라이팬", "칼", "도마", "그릇", "수저", "오프너", "컵", "잔", "텀블러", "주방수납", "정리", "밀폐저장", "주방잡화", "종이컵", "유아식기", "베이킹용품", "스테인리스", "홈세트", "에어프라이기",

                        //생활용품
                        "샴푸", "린스", "트리트먼트", "옷걸이", "화장지", "수납", "정리", "물티슈", "섬유유연제", "세정제", "마스크", "가글", "탈취", "디퓨저", "칫솔", "바디워시", "종이컵", "모기",

                        //문구/오피스
                        "필통", "앨범", "메모지", "다이어리", "연필꽂이", "펜", "샤프", "연필", "노트", "스티커", "독서대", "화이트", "시계", "연필깎이", "북마크", "클립", "테이프", "수납함", "받침대",

                        //반려동물
                        "사료", "간식", "영양제", "하우스", "울타리", "장난감", "브러쉬", "이동장", "캣타워", "목줄", "쳇바퀴", "베딩", "채집통", "샴푸", "급수식기", "매트",

                        //헬스 건강 식품
                        "홈트레이닝", "요가", "영양제", "비타민", "홍삼", "건강의료용품", "건강가전", "건강식품", "소독", "미네랄", "건강즙", "자연추출물", "헬스보충식품", "다이어트", "한방재료", "영양식", "의료용품", "측정용품", "꿀", "샐러드",

                        //여행
                        "국내여행", "해외여행", "패키지", "자유여행", "항공권", "호텔", "티켓", "렌터카", "리조트", "골프", "투어", "체험", "공연", "유람선", "뷔페", "키즈", "테마파크", "스파", "워터파크", "공간대여",

                        //도서
                        "주식책", "it책", "c언어책", "네트워크책", "배스트셀러", "베스트셀러", "자기계발", "소설", "종교책", "컴퓨터책", "비트코인책", "정치서적",

                        //완구
                        "큐브", "색연필", "볼링세트", "리코더", "단소", "실로폰", "젠가", "루미큐브", "보드게임", "퍼즐", "레고", "인형", "곰인형",

                        //인테리어
                        "이불", "패드", "토퍼", "침대커버", "베개", "커튼", "블라인드", "소파", "쿠션", "카펫", "카페트", "실내화", "청소", "이사", "정리", "수납", "의자", "테이블", "식탁", "화장대", "조명", "인테리어", "꾸미기", "수납장", "정리", "데코", "캔들", "디퓨저", "방향제", "화병", "꽃", "조화", "선반", "DIY", "욕실용품",

                        //가전디지털
                        "TV", "가전", "가전제품", "냉장고", "세탁기", "건조기", "청소", "드라이기", "고데기", "스타일러", "네일", "밥솥", "전자레인지", "오븐", "가스레인지", "식기세척기", "믹서기", "전기포트", "에어프라이어", "에어프라이기", "토스터", "노트북", "데스크탑", "태블릿", "아이패드", "키보드", "마우스", "USB", "프린터", "헤드셋", "공유기", "휴대폰", "카메라", "케이스", "보조배터리",

                        //스포츠/레저
                        "캠핑", "차박", "오토캠핑", "미니멀캠핑", "텐트", "간이의자", "침낭", "해먹", "캠핑", "버너", "숯", "식기", "아이스박스", "유산소", "운동", "트레이닝", "스포츠", "수영복", "레쉬가드", "아쿠아슈즈", "아쿠아신발", "서핑", "자전거", "킥보드", "낚시", "등산", "배낭", "운동화"

                )
                var execepted_gift_list1 = execepted_gift_list.subList(0, execepted_gift_list.indexOf("휘링") + 1)
                var execepted_gift_list2 = execepted_gift_list.subList(execepted_gift_list.indexOf("티셔츠"), execepted_gift_list.indexOf("반팔") + 1)
                var execepted_gift_list3 = execepted_gift_list.subList(execepted_gift_list.indexOf("스킨"), execepted_gift_list.indexOf("블러셔") + 1)
                var execepted_gift_list4 = execepted_gift_list.subList(execepted_gift_list.indexOf("배냇저고리"), execepted_gift_list.indexOf("젖병세척") + 1)
                var execepted_gift_list5 = execepted_gift_list.subList(execepted_gift_list.indexOf("과일"), execepted_gift_list.indexOf("마라탕") + 1)
                var execepted_gift_list6 = execepted_gift_list.subList(execepted_gift_list.indexOf("냄비"), execepted_gift_list.indexOf("에어프라이기") + 1)
                var execepted_gift_list7 = execepted_gift_list.subList(execepted_gift_list.indexOf("샴푸"), execepted_gift_list.indexOf("모기") + 1)
                var execepted_gift_list8 = execepted_gift_list.subList(execepted_gift_list.indexOf("필통"), execepted_gift_list.indexOf("받침대") + 1)
                var execepted_gift_list9 = execepted_gift_list.subList(execepted_gift_list.indexOf("사료"), execepted_gift_list.indexOf("매트") + 1)
                var execepted_gift_list10 = execepted_gift_list.subList(execepted_gift_list.indexOf("홈트레이닝"), execepted_gift_list.indexOf("샐러드") + 1)
                var execepted_gift_list11 = execepted_gift_list.subList(execepted_gift_list.indexOf("국내여행"), execepted_gift_list.indexOf("공간대여") + 1)
                var execepted_gift_list12 = execepted_gift_list.subList(execepted_gift_list.indexOf("주식책"), execepted_gift_list.indexOf("정치서적") + 1)
                var execepted_gift_list13 = execepted_gift_list.subList(execepted_gift_list.indexOf("큐브"), execepted_gift_list.indexOf("곰인형") + 1)
                var execepted_gift_list14 = execepted_gift_list.subList(execepted_gift_list.indexOf("이불"), execepted_gift_list.indexOf("욕실용품") + 1)
                var execepted_gift_list15 = execepted_gift_list.subList(execepted_gift_list.indexOf("TV"), execepted_gift_list.indexOf("보조배터리") + 1)
                var execepted_gift_list16 = execepted_gift_list.subList(execepted_gift_list.indexOf("캠핑"), execepted_gift_list.indexOf("운동화") + 1)
                var execepted_gift_group_list = arrayOf(execepted_gift_list1, execepted_gift_list2, execepted_gift_list3, execepted_gift_list4, execepted_gift_list5, execepted_gift_list6, execepted_gift_list7, execepted_gift_list8, execepted_gift_list9, execepted_gift_list10, execepted_gift_list11, execepted_gift_list12, execepted_gift_list13, execepted_gift_list14, execepted_gift_list15, execepted_gift_list16)
                var execepted_gift_list_offline: List<String> = arrayListOf(
                        "(이마트)OR(하이마트)OR(차량용품 백화점)",
                        "(옷가게)OR(백화점)",
                        "(더페이스샵)OR(토니모리)OR(올리브영)OR(올리브영)",
                        "(백화점)",
                        "(트레이더스)OR(백화점)OR(노브랜드)OR(자연드림)",
                        "(백화점)OR(이케아)OR(주방용품파는곳)",
                        "(백화점)OR(이케아)OR(생활용품파는곳)OR(GS25)OR(노브랜드)",
                        "(문구용품파는곳)OR(백화점)OR(알파문구)OR(서점)",
                        "(애견용품)OR(백화점)",
                        "(백화점)OR(노브랜드)OR(트레이더스)",
                        "여행예약",
                        "서점",
                        "(백화점)OR(장난감 가게)OR(서점)",
                        "(인테리어 용품)OR(이케아)",
                        "(하이마트)OR(전자제품)OR(백화점)",
                        "(캠핑용품 판매)OR(백화점)")

                // 인텐트로 받은 키워드 인텐트로 받는 값은 offline_item 에 넣을 것
                var offline_item = "티셔츠" // 여기 수정
                var offline_item_result = 0
                var offline_mart_keyword = ""
                for (execepted_gift_group_index in execepted_gift_group_list.indices) {
                    for (execepted_gift_index in execepted_gift_group_list[execepted_gift_group_index].indices) {
                        if (offline_item.equals(execepted_gift_group_list[execepted_gift_group_index][execepted_gift_index])) {
                            offline_item_result = execepted_gift_group_index
                            break
                        }
                    }
                }
                if (offline_item_result != 0) {
                    offline_mart_keyword = execepted_gift_list_offline[offline_item_result]
                    var url: String = "https://www.google.com/maps/search/" + offline_mart_keyword
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } else {
                    Log.d("test", "지도검색 에러 확인")
                }
            })
            dlg.setNeutralButton("온라인", DialogInterface.OnClickListener { dialog, which ->
                var word : String = myarray[position].toString()

                var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                var uri = Uri.parse(url)
                var intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            })
            dlg.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which -> })
            dlg.show()

        }


        listView2.setOnItemClickListener { parent, view, position, id ->
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("검색하기") //제목
            dlg.setMessage("오프라인 매장과 온라인 매장을 검색할 수 있어요") // 메시지
            dlg.setPositiveButton("오프라인", DialogInterface.OnClickListener { dialog, which ->
                var execepted_gift_list: List<String> = arrayListOf(
                        // 자동차 용품
                        "스마트폰 거치대", "방향제", "카시트 커버", "카시트", "세차", "차량관리용품", "안전삼각대", "차량용소화기", "주차번호판", "차량용 청소기", "차량용 미니 청소기", "차량용", "불스원", "더존", "휘링",

                        //패션의류 / 잡화
                        "티셔츠", "맨투맨", "후드티", "블라우스", "셔츠", "원피스", "정장", "바지", "레깅스", "스커트", "치마", "트레이닝복", "후드집업", "니트", "조끼", "아우터", "잠옷", "커플룩", "패밀리룩", "스포츠의류", "임산부의류", "한복", "수의", "신발", "가방", "슬랙스", "에코백", "캔버스화", "양말", "반팔",

                        //뷰티
                        "스킨", "로션", "에센스", "세럼", "앰플", "미스트", "오일", "크림", "올인원", "마스크", "팩", "선케어", "태닝", "클렌징폼", "클렌징오일", "프라이머", "아이리무버", "클렌징티슈", "필링", "아이라이너", "아이섀도", "마스카라", "아이브로우", "틴트", "립글로스", "립스틱", "파운데이션", "컨실러", "메이크업픽서", "블러셔",

                        //육아
                        "배냇저고리", "수유복", "젖병건조대", "롬퍼", "스패츠", "보행기", "걸음마신발", "신생아모자", "손싸개", "발싸개", "겉싸개", "속싸개", "가제수건", "턱받이", "스카프빕", "배가리개", "출산선물", "돌반지", "미아방지가방", "미아방지주얼리", "기저귀", "일회용기저귀", "천기저귀", "배변훈련팬티", "기저귀크림", "기저귀파우더", "분유", "이유식", "젖병", "젖병세척",
                        //식품
                        "과일", "견과", "채소", "쌀", "잡곡", "계란", "음료", "커피", "원두", "차", "과자", "초콜릿", "시리얼", "씨리얼", "드레싱", "오일", "유제품", "유기농", "프로틴", "대용식", "마라탕",

                        //주방용품
                        "냄비", "프라이팬", "칼", "도마", "그릇", "수저", "오프너", "컵", "잔", "텀블러", "주방수납", "정리", "밀폐저장", "주방잡화", "종이컵", "유아식기", "베이킹용품", "스테인리스", "홈세트", "에어프라이기",

                        //생활용품
                        "샴푸", "린스", "트리트먼트", "옷걸이", "화장지", "수납", "정리", "물티슈", "섬유유연제", "세정제", "마스크", "가글", "탈취", "디퓨저", "칫솔", "바디워시", "종이컵", "모기",

                        //문구/오피스
                        "필통", "앨범", "메모지", "다이어리", "연필꽂이", "펜", "샤프", "연필", "노트", "스티커", "독서대", "화이트", "시계", "연필깎이", "북마크", "클립", "테이프", "수납함", "받침대",

                        //반려동물
                        "사료", "간식", "영양제", "하우스", "울타리", "장난감", "브러쉬", "이동장", "캣타워", "목줄", "쳇바퀴", "베딩", "채집통", "샴푸", "급수식기", "매트",

                        //헬스 건강 식품
                        "홈트레이닝", "요가", "영양제", "비타민", "홍삼", "건강의료용품", "건강가전", "건강식품", "소독", "미네랄", "건강즙", "자연추출물", "헬스보충식품", "다이어트", "한방재료", "영양식", "의료용품", "측정용품", "꿀", "샐러드",

                        //여행
                        "국내여행", "해외여행", "패키지", "자유여행", "항공권", "호텔", "티켓", "렌터카", "리조트", "골프", "투어", "체험", "공연", "유람선", "뷔페", "키즈", "테마파크", "스파", "워터파크", "공간대여",

                        //도서
                        "주식책", "it책", "c언어책", "네트워크책", "배스트셀러", "베스트셀러", "자기계발", "소설", "종교책", "컴퓨터책", "비트코인책", "정치서적",

                        //완구
                        "큐브", "색연필", "볼링세트", "리코더", "단소", "실로폰", "젠가", "루미큐브", "보드게임", "퍼즐", "레고", "인형", "곰인형",

                        //인테리어
                        "이불", "패드", "토퍼", "침대커버", "베개", "커튼", "블라인드", "소파", "쿠션", "카펫", "카페트", "실내화", "청소", "이사", "정리", "수납", "의자", "테이블", "식탁", "화장대", "조명", "인테리어", "꾸미기", "수납장", "정리", "데코", "캔들", "디퓨저", "방향제", "화병", "꽃", "조화", "선반", "DIY", "욕실용품",

                        //가전디지털
                        "TV", "가전", "가전제품", "냉장고", "세탁기", "건조기", "청소", "드라이기", "고데기", "스타일러", "네일", "밥솥", "전자레인지", "오븐", "가스레인지", "식기세척기", "믹서기", "전기포트", "에어프라이어", "에어프라이기", "토스터", "노트북", "데스크탑", "태블릿", "아이패드", "키보드", "마우스", "USB", "프린터", "헤드셋", "공유기", "휴대폰", "카메라", "케이스", "보조배터리",

                        //스포츠/레저
                        "캠핑", "차박", "오토캠핑", "미니멀캠핑", "텐트", "간이의자", "침낭", "해먹", "캠핑", "버너", "숯", "식기", "아이스박스", "유산소", "운동", "트레이닝", "스포츠", "수영복", "레쉬가드", "아쿠아슈즈", "아쿠아신발", "서핑", "자전거", "킥보드", "낚시", "등산", "배낭", "운동화"

                )
                var execepted_gift_list1 = execepted_gift_list.subList(0, execepted_gift_list.indexOf("휘링") + 1)
                var execepted_gift_list2 = execepted_gift_list.subList(execepted_gift_list.indexOf("티셔츠"), execepted_gift_list.indexOf("반팔") + 1)
                var execepted_gift_list3 = execepted_gift_list.subList(execepted_gift_list.indexOf("스킨"), execepted_gift_list.indexOf("블러셔") + 1)
                var execepted_gift_list4 = execepted_gift_list.subList(execepted_gift_list.indexOf("배냇저고리"), execepted_gift_list.indexOf("젖병세척") + 1)
                var execepted_gift_list5 = execepted_gift_list.subList(execepted_gift_list.indexOf("과일"), execepted_gift_list.indexOf("마라탕") + 1)
                var execepted_gift_list6 = execepted_gift_list.subList(execepted_gift_list.indexOf("냄비"), execepted_gift_list.indexOf("에어프라이기") + 1)
                var execepted_gift_list7 = execepted_gift_list.subList(execepted_gift_list.indexOf("샴푸"), execepted_gift_list.indexOf("모기") + 1)
                var execepted_gift_list8 = execepted_gift_list.subList(execepted_gift_list.indexOf("필통"), execepted_gift_list.indexOf("받침대") + 1)
                var execepted_gift_list9 = execepted_gift_list.subList(execepted_gift_list.indexOf("사료"), execepted_gift_list.indexOf("매트") + 1)
                var execepted_gift_list10 = execepted_gift_list.subList(execepted_gift_list.indexOf("홈트레이닝"), execepted_gift_list.indexOf("샐러드") + 1)
                var execepted_gift_list11 = execepted_gift_list.subList(execepted_gift_list.indexOf("국내여행"), execepted_gift_list.indexOf("공간대여") + 1)
                var execepted_gift_list12 = execepted_gift_list.subList(execepted_gift_list.indexOf("주식책"), execepted_gift_list.indexOf("정치서적") + 1)
                var execepted_gift_list13 = execepted_gift_list.subList(execepted_gift_list.indexOf("큐브"), execepted_gift_list.indexOf("곰인형") + 1)
                var execepted_gift_list14 = execepted_gift_list.subList(execepted_gift_list.indexOf("이불"), execepted_gift_list.indexOf("욕실용품") + 1)
                var execepted_gift_list15 = execepted_gift_list.subList(execepted_gift_list.indexOf("TV"), execepted_gift_list.indexOf("보조배터리") + 1)
                var execepted_gift_list16 = execepted_gift_list.subList(execepted_gift_list.indexOf("캠핑"), execepted_gift_list.indexOf("운동화") + 1)
                var execepted_gift_group_list = arrayOf(execepted_gift_list1, execepted_gift_list2, execepted_gift_list3, execepted_gift_list4, execepted_gift_list5, execepted_gift_list6, execepted_gift_list7, execepted_gift_list8, execepted_gift_list9, execepted_gift_list10, execepted_gift_list11, execepted_gift_list12, execepted_gift_list13, execepted_gift_list14, execepted_gift_list15, execepted_gift_list16)
                var execepted_gift_list_offline: List<String> = arrayListOf(
                        "(이마트)OR(하이마트)OR(차량용품 백화점)",
                        "(옷가게)OR(백화점)",
                        "(더페이스샵)OR(토니모리)OR(올리브영)OR(올리브영)",
                        "(백화점)",
                        "(트레이더스)OR(백화점)OR(노브랜드)OR(자연드림)",
                        "(백화점)OR(이케아)OR(주방용품파는곳)",
                        "(백화점)OR(이케아)OR(생활용품파는곳)OR(GS25)OR(노브랜드)",
                        "(문구용품파는곳)OR(백화점)OR(알파문구)OR(서점)",
                        "(애견용품)OR(백화점)",
                        "(백화점)OR(노브랜드)OR(트레이더스)",
                        "여행예약",
                        "서점",
                        "(백화점)OR(장난감 가게)OR(서점)",
                        "(인테리어 용품)OR(이케아)",
                        "(하이마트)OR(전자제품)OR(백화점)",
                        "(캠핑용품 판매)OR(백화점)")

                // 인텐트로 받은 키워드 인텐트로 받는 값은 offline_item 에 넣을 것
                var offline_item = "티셔츠" // 여기 수정
                var offline_item_result = 0
                var offline_mart_keyword = ""
                for (execepted_gift_group_index in execepted_gift_group_list.indices) {
                    for (execepted_gift_index in execepted_gift_group_list[execepted_gift_group_index].indices) {
                        if (offline_item.equals(execepted_gift_group_list[execepted_gift_group_index][execepted_gift_index])) {
                            offline_item_result = execepted_gift_group_index
                            break
                        }
                    }
                }
                if (offline_item_result != 0) {
                    offline_mart_keyword = execepted_gift_list_offline[offline_item_result]
                    var url: String = "https://www.google.com/maps/search/" + offline_mart_keyword
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } else {
                    Log.d("test", "지도검색 에러 확인")
                }
            })
            dlg.setNeutralButton("온라인", DialogInterface.OnClickListener { dialog, which ->
                var word : String = myarray[position].toString()

                var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                var uri = Uri.parse(url)
                var intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            })
            dlg.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which -> })
            dlg.show()

        }


        /*listView1.setOnItemClickListener{ parent, view, position, id ->////////////////////////////////////////////////////
            var word : String = topten[position].toString()

            var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
            var uri = Uri.parse(url)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }*/

        back3.setOnClickListener{ // 뒤로 버튼
            finish()
        }


        button3.setOnClickListener{ // 홈으로 버튼
            val intent= Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        button4.setOnClickListener{ // 다른 대화내용 불러오기 버튼
            var intent = Intent(applicationContext, GiftActivity::class.java)
            startActivity(intent)
        }

        button7.setOnClickListener{// 필터링 내용 저장하기 버튼

            var fbAuth = FirebaseAuth.getInstance()
            var fbFire = FirebaseFirestore.getInstance()

            var uid = fbAuth?.uid.toString()
            var uemail = fbAuth?.currentUser?.email.toString()
            var txtName : String? = txtFileName
            var list1 : MutableList<String> = myarray.toMutableList()
            var list2 : MutableList<String> = topten.toMutableList()

            if (txtName != null) {
                writeNewUser(uid, uid, uemail, txtName, list1, list2)
            }
        }

        button6.setOnClickListener{// 마이페이지
            var intent = Intent(applicationContext, MypageActivity::class.java)
            startActivity(intent)
        }


        //        도움말
        question1.setOnClickListener {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("원한다. 선물") //제목
            dlg.setMessage("친구가 갖고싶다고 직접 말한 단어들이에요! 이겁니다! 2거에요! ~(@v@)/") // 메시지
            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
            dlg.show()
        }

        question2.setOnClickListener {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("원할껄? 선물") //제목
            dlg.setMessage("대화 내용 중 친구의 언급이 많았던 단어들이에요! 친구의 관심사에 가깝지 않을까요?") // 메시지
            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
            dlg.show()
        }

    }
}


class FireMissilesDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("원하는 형태의 매장을 선택해주세요")
                    .setPositiveButton("온라인 매장",
                            DialogInterface.OnClickListener { dialog, id ->
                                // FIRE ZE MISSILES!
                                //네이버 쇼핑 사이트 연결 코드
                            })
                    .setNeutralButton("오프라인 매장",
                            DialogInterface.OnClickListener { dialog, id ->
                                //지도 보여주는 코드
                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog.
                                //아무것도 안넣어도 될 것 같은데 오류 나면 보자!
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}









/*for(i in 0..myarray.count()-1) { // 버튼 생성
            var btn = Button(this).apply{
                setOnClickListener{
                    var word : String = myarray[i]

                    var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.id = i.toInt()
            btn.setText(myarray[i])
            linear1.addView(btn)
        }
        for(i in 0..topten.count()-1) { // 버튼 생성
            var btn = Button(this).apply{
                setOnClickListener{
                    var word : String = topten[i]
                    var url : String = "https://search.shopping.naver.com/search/all?query=" + word + "&cat_id=&frm=NVSHATC"
                    var uri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.id = (i+10).toInt()
            btn.setText(topten[i])
            linear2.addView(btn)
        }*/