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
const val API_CHECK_NAME = 101
const val API_POST_FCM_TOKEN = 102

const val API_JOIN_ACCOUNT = 110
const val API_POST_OAUTH_TOKEN = 111
const val API_POST_GOOGLE_OAUTH_TOKEN = 112  // 나중에 합쳐질 API
const val API_GET_GOOGLE_ACCESS_TOKEN = 113
const val API_CHECK_EMAIL = 114
const val API_USER_RESIGN = 115

const val API_EMAIL_GET_NAVER = 150

const val API_GET_USER_PROFILE = 160
const val API_FOLLOW_USER = 161
const val API_UNFOLLOW_USER = 162
const val API_REPORT_USER =163
const val API_GET_REPORT_LIST = 164
const val API_CANCEL_REPORT = 165

const val API_GET_FOLLOWER_LIST = 170
const val API_GET_FOLLOWING_LIST = 171

const val API_GET_MY_PROFILE = 180
const val API_EDIT_MY_PROFILE = 181
const val API_EDIT_MY_IMAGE = 182
const val API_EDIT_MY_INTEREST = 183
const val API_GET_SEARCH_HISTORY = 184
const val API_DELETE_SEARCH_HISTORY = 185
const val API_DELETE_ALL_SEARCH_HISTORY = 186
const val API_GET_MY_WISH_LIST = 187

const val API_CERTIFY_UPLOAD_IMG = 190
const val API_CERTIFY_UNIVERSITY = 191
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
const val API_DELETE_MARKET_PRODUCT = 212
const val API_PRODUCT_UPLOAD_IMG = 220
const val API_PRODUCT_DELETE_IMG = 221


const val API_GET_PRODUCT_DETAIL = 230
const val API_POST_LIKE_PRODUCT = 240
const val API_POST_WISH_PRODUCT = 241
const val API_POST_SUGGEST_PRODUCT = 242


const val API_GET_MARKET_WORK_DETAIL = 255
const val API_BUILD_MARKET_WORK = 250
const val API_UPDATE_MARKET_WORK = 251
const val API_DELETE_MARKET_WORK = 252
const val API_WORK_UPLOAD_IMG = 253
const val API_WORK_DELETE_IMG = 254
const val API_POST_LIKE_WORK = 256
const val API_POST_WISH_WORK = 257
const val API_POST_SUGGEST_WORK = 258


const val API_GET_MARKET_WORKS = 260



const val API_GET_HOME_BANNER = 270


const val API_GET_CHATROOM_LIST = 400
const val API_GET_CHATROOM_POST_LIST = 401
const val API_CREATE_CHATROOM = 410
const val API_GET_CHATROOM_MSG = 402
const val API_GET_CHATROOM_DETAIL = 403
const val API_CHAT_UPLOAD_IMG = 404


