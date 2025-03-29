
const KakaoLoginType2: React.FC = () => {
    const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY;
    const REDIRECT_URI =import.meta.env.VITE_KAKAO_REDIRECT_URI_TYPE2;
    const URL = import.meta.env.VITE_KAKAO_OAUTH_URL;

    const kakaoAuthURL = `${URL}?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

    const handleLogin = () => {
        window.location.href = kakaoAuthURL;
    };

    return (
        <button onClick={handleLogin}>카카오 로그인</button>
    )
}

export default KakaoLoginType2;