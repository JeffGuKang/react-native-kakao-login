export default class RNKakao {
    /**
     * Login through kakaotalk
     */
    public static login(): Promise<KakaoUser>

    /**
     * Login with all possible types as kakaotalk, kakaostory and web
     */
    public static loginWithAllTypes(): Promise<KakaoUser>

    public static logout(): Promise<{}>

    public static userInfo(): Promise<KakaoUser>
}

export interface KakaoUser {
    id: string
    accessToken: string
    nickName: string | null
    email: string | null
    profileImage: string | null
    profileImageThumbnail: string | null
    ageRange: string | null
    gender: string | null
}
