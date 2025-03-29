declare global {
    interface Window {
        naver: any;
    }
}

const NaverLoginType2: React.FC = () => {
    const { naver } = window;

    const handleNaverLogin = () => {
        if (!naver) return;
        const naverOauthUrl = import.meta.env.VITE_NAVER_OAUTH_URL
        const clientId = import.meta.env.VITE_NAVER_CLIENT_ID;
        const callbackUrl = import.meta.env.VITE_NAVER_TYPE2_CALLBACK_URL;
        const state = Math.random().toString(36).substring(2, 15)

        window.location.href = `${naverOauthUrl}?response_type=code&client_id=${clientId}&redirect_uri=${callbackUrl}&state=${state}`
    }

    return (
        <div>
            <button onClick={handleNaverLogin}>
                네이버 로그인
            </button>
        </div>
    )
}
export default NaverLoginType2;