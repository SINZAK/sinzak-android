package io.sinzak.android.constants


/**
 *
 *
 * API NUM 규칙
 * 100번대 : USER 관련 API
 * 200번대 : MARKET, PRODUCT 관련 API
 * 300번대 : 게시글 관련 API
 * 400번대 : CHAT 관련 API
 */



const val API_REFRESH_TOKEN = 1

const val API_LOGIN_EMAIL = 100
const val API_JOIN_ACCOUNT = 110

const val API_EMAIL_GET_NAVER = 150

const val API_GET_USER_PROFILE = 160
const val API_FOLLOW_USER = 161
const val API_UNFOLLOW_USER = 162

const val API_GET_FOLLOWER_LIST = 170
const val API_GET_FOLLOWING_LIST = 171

const val API_GET_MY_PROFILE = 180
const val API_EDIT_MY_PROFILE = 181

const val API_CERTIFY_UPLOAD_IMG = 190
const val API_CERTIFY_UNIVERITY = 191
const val API_SEND_MAIL_CODE = 192
const val API_CHECK_MAIL_CODE = 193

const val API_GET_MARKET_PRODUCTS = 200
const val API_GET_HOME_PRODUCTS = 201
const val API_GET_HOME_REFER = 202
const val API_GET_HOME_RECENT = 203
const val API_GET_HOME_FOLLOWING = 204
const val API_GET_HOME_RECOMMEND = 205



const val API_BUILD_MARKET_PRODUCT = 210
const val API_UPDATE_MARKET_PRODUCT = 211
const val API_PRODUCT_UPLOAD_IMG = 220
const val API_PRODUCT_DELETE_IMG = 221

const val API_GET_PRODUCT_DETAIL = 230
const val API_POST_LIKE_PRODUCT = 240
const val API_POST_WISH_PRODUCT = 241
