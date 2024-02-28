package com.adevspoon.api.common.utils

import org.springframework.stereotype.Component

private val adjectives = mutableListOf(
    "행복한", "최고의", "기쁜", "신이 난", "재미있는",
    "활기찬", "졸린", "배고픈", "목마른", "현명한",
    "멋진", "용감한", "친절한", "창의적인", "낭만적인",
    "긍정적인", "낙천적인", "자유로운", "감사하는", "강한"
)

private val animals = mutableListOf(
    "강아지", "고양이", "시츄", "물고기", "거북이",
    "토끼", "햄스터", "말", "돼지", "송아지",
    "사자", "호랑이", "곰", "코끼리", "기린",
    "원숭이", "뱀", "악어", "부엉이", "고릴라"
)

@Component
class NicknameProcessor {
    fun createRandomNickname(): String {
        return "${adjectives.random()} ${animals.random()}"
    }
}