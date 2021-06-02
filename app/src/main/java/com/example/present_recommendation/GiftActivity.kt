package com.example.present_recommendation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.present_recommendation.R
import com.example.present_recommendation.RankingActivity
import kotlinx.android.synthetic.main.activity_gift.*
import okhttp3.internal.toImmutableList
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.math.log

class GiftActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)



        // back logic

        // 카카오톡 파일경로 읽기
        //val txtFilePath = Environment.getExternalStorageDirectory().absolutePath + "/KakaoTalk/Chats"
        val txtFilePath = "/data/data/com.example.present_recommendation/files/KakaoTalk"
        val txtFileName = "KakaoTalkChats.txt"
        // 파일 읽기
        // 파일 읽기
        Log.d("test",txtFilePath)
        var strong_recommend_list_counter = List<Int>(300) { 0 }.toMutableList()// 강력추천
        var recommend_list_counter = List<Int>(300) { 0 }.toMutableList() // 일반추천

        var strong_recommend_list_info = mutableMapOf<String,Int>()// 강력추천정보
        var recommend_list_info = mutableMapOf<String,Int>()// 일반 추천 정보

        var wanted_identified_array:List<String> = arrayListOf("갖고 싶", "갖고싶", "사고 싶", "사고싶", "사줘", "사주라", "사주세요", "가지고싶어요")
        var execepted_gift_list: List<String> = arrayListOf(
            // 자동차 용품
            "스마트폰 거치대","방향제", "카시트 커버", "카시트", "세차", "차량관리용품", "안전삼각대", "차량용소화기", "주차번호판", "차량용 청소기", "차량용 미니 청소기","차량용","마라탕","불스원","더존","휘링",

            //패션의류 / 잡화
            "티셔츠", "맨투맨", "후드티", "블라우스", "셔츠", "원피스", "정장", "바지", "레깅스", "스커트", "치마", "트레이닝복", "후드집업", "니트", "조끼", "아우터", "잠옷", "커플룩", "패밀리룩", "스포츠의류", "임산부의류", "한복", "수의", "신발", "가방", "슬랙스", "에코백", "캔버스화", "양말", "반팔",

            //뷰티
            "스킨", "로션", "에센스", "세럼", "앰플", "미스트", "오일", "크림", "올인원", "마스크", "팩", "선케어", "태닝", "클렌징폼", "클렌징오일", "프라이머", "아이리무버", "클렌징티슈", "필링", "아이라이너", "아이섀도", "마스카라", "아이브로우", "틴트", "립글로스", "립스틱", "파운데이션", "컨실러", "메이크업픽서", "블러셔",

            //육아
            "배냇저고리", "수유복", "젖병건조대","롬퍼", "스패츠", "보행기", "걸음마신발", "신생아모자", "손싸개", "발싸개", "겉싸개", "속싸개", "가제수건", "턱받이", "스카프빕", "배가리개", "출산선물", "돌반지", "미아방지가방", "미아방지주얼리", "기저귀", "일회용기저귀", "천기저귀", "배변훈련팬티", "기저귀크림", "기저귀파우더", "분유", "이유식", "젖병", "젖병세척",
            //식품
            "과일","견과","채소","쌀","잡곡", "계란","음료","커피","원두","차", "과자","초콜릿","시리얼","씨리얼","드레싱", "오일","유제품","유기농","프로틴","대용식",

            //주방용품
            "냄비","프라이팬","칼","도마","그릇","수저","오프너","컵","잔","텀블러", "주방수납","정리","밀폐저장","주방잡화","종이컵", "유아식기","베이킹용품","스테인리스","홈세트","에어프라이기",

            //생활용품
            "샴푸","린스","트리트먼트","옷걸이","화장지", "기저귀","세제","수납","정리","물티슈", "섬유유연제","세정제","마스크","가글","탈취", "디퓨저","칫솔","바디워시","종이컵","모기",

            //문구/오피스
            "필통","앨범","메모지","다이어리","연필꽂이", "펜","샤프","연필","노트","스티커", "독서대","화이트","시계","연필깎이","북마크", "클립","테이프","수납함","받침대","매트",

            //반려동물
            "사료", "간식", "영양제", "하우스", "울타리", "장난감", "브러쉬", "이동장", "캣타워", "목줄", "쳇바퀴", "베딩", "채집통", "샴푸", "급수식기", "매트",

            //헬스 건강 식품
            "홈트레이닝", "요가", "영양제", "비타민", "홍삼", "건강의료용품", "건강가전" ,"건강식품", "소독", "미네랄", "건강즙", "자연추출물", "헬스보충식품", "다이어트", "한방재료", "영양식", "의료용품", "측정용품", "꿀", "샐러드",

            //여행
            "국내여행", "해외여행", "패키지", "자유여행", "항공권", "호텔", "티켓", "렌터카", "리조트", "골프", "투어", "체험", "공연", "유람선", "뷔페", "키즈", "테마파크", "스파", "워터파크", "공간대여",

            //도서
            "주식책", "it책", "c언어책", "네트워크책", "배스트셀러", "베스트셀러", "자기계발", "소설", "종교책", "컴퓨터책", "비트코인책", "정치서적",

            //완구
            "큐브", "색연필", "볼링세트", "리코더", "단소", "실로폰", "젠가", "루미큐브", "보드게임", "퍼즐", "레고", "인형","곰인형"
        )

        val text = StringBuilder()
        var br: BufferedReader? = null
        try {
            val file = File(txtFilePath+"/"+txtFileName)
            br = BufferedReader(FileReader(file))
            for (line in br.lines()) {
                text.append(line)
                text.append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (br != null) {
                br.close()
            }
        }

        /**
         *
         * raw_chat_sentence: 읽어온 채팅 그대로의 가공 안한 상태 형태 리스트: ex) 2021년 3월 27일 오후 9:18, 김기현 : ㅇㅈ?
         * processed_chat_sentence: 읽어온 채팅을 가공하여 대화내용만 추출한 문장모아놓은 리스트: ex) ㅇㅈ?
         * strong_recommend_list: 가지고 싶다는 의미를 내포한 어구 리스트
         */
        val raw_chat_sentence : MutableList<String> = text.split('\n').toMutableList()
        val processed_chat_sentence : MutableList<String> = text.split('\n').toMutableList()

        for (i in 3..raw_chat_sentence.lastIndex){
            val delimiter_index = raw_chat_sentence[i].indexOf(":",20)
            if (delimiter_index!=-1){
                processed_chat_sentence[i] =raw_chat_sentence[i].slice(delimiter_index+2..raw_chat_sentence[i].lastIndex)
                //print( processed_chat_sentence[i]+'\n')
            }
        }
        for (i in 3..raw_chat_sentence.lastIndex){
            val delimiter_index = raw_chat_sentence[i].indexOf(":",20)
            if (delimiter_index!=-1) {

                //가지고 싶다는 의미를 내포한 어구들을 하나하나 찾아봄
                for (words in wanted_identified_array){

                    // 가지고 싶다는 의미를 내포하는 대화 내용이라면
                    if (processed_chat_sentence[i].contains(words)){
                        for (execepted_gift_index in execepted_gift_list.indices){
                            if(processed_chat_sentence[i].contains(execepted_gift_list[execepted_gift_index])){
                                strong_recommend_list_counter[execepted_gift_index]+=1
                            }
                        }
                    }else // 아니라면 일반 추천
                    {
                        for (execepted_gift_index in execepted_gift_list.indices){
                            if(processed_chat_sentence[i].contains(execepted_gift_list[execepted_gift_index])){
                                recommend_list_counter[execepted_gift_index]+=1
                            }
                        }
                    }
                }
            }
        }
        Log.d("test","일반 추천")
        for (i in recommend_list_counter.indices){
            if (recommend_list_counter[i]!=0){
                recommend_list_info.put(execepted_gift_list[i],recommend_list_counter[i])
            }
        }
        var count = 0

        var recommend_list_info_sortedByValue = recommend_list_info.toList().sortedWith(compareByDescending({it.second})).toMap()
        var recommend_list_top_10:MutableList<String> = arrayListOf()

        for ((key,value) in recommend_list_info_sortedByValue){
            //Log.d("test",key+" : "+value)
            recommend_list_top_10.add(count,key)
            if (count>=9){
                break
            }
            count++
        }
        for (i in recommend_list_top_10){ // 그냥 top 10
            Log.d("test",i)
        }
        Log.d("test","강력 추천")

        for (i in strong_recommend_list_counter.indices){
            if (strong_recommend_list_counter[i]!=0){
                strong_recommend_list_info.put(execepted_gift_list[i],strong_recommend_list_counter[i])
            }
        }
        count = 0
        var strong_recommend_list_info_sortedByValue = strong_recommend_list_info.toList().sortedWith(compareByDescending({it.second})).toMap()
        var strong_recommend_list_top_10:MutableList<String> = arrayListOf()
        for ((key,value) in strong_recommend_list_info_sortedByValue){
            //Log.d("test",key+" : "+value)

            strong_recommend_list_top_10.add(count,key)
            if (count>=9){
                break
            }
            count++
        }
        for (i in strong_recommend_list_top_10){ // 갖고싶다 키워드
            Log.d("test",i)
        }

        bringok.setOnClickListener{
            var intent = Intent(applicationContext, RankingActivity::class.java)

            var myarray : Array<String> = arrayOf()
            myarray = recommend_list_top_10.toTypedArray()


            var topten : Array<String> = arrayOf()
            topten = strong_recommend_list_top_10.toTypedArray()


            intent.putExtra("recommend_list_top_10", myarray)
            intent.putExtra("strong_recommend_list_top_10", topten)

            intent.putExtra("txtFileName", txtFileName) // txt 이름 가져가기기


           startActivity(intent)
        }

    }

}