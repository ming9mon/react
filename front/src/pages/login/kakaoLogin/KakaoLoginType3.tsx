import {useEffect} from "react";

declare global {
    interface Window {
        Kakao: any;
    }
}

const KakaoLoginType3: React.FC = () => {
    const KAKAO_JS_KEY = import.meta.env.VITE_KAKAO_JS_KEY;
    const KAKAO_REDIRECT_URL = import.meta.env.VITE_KAKAO_REDIRECT_URI_TYPE1;

    useEffect(() => {
        if (window.Kakao && !window.Kakao.isInitialized()) {
            window.Kakao.init(KAKAO_JS_KEY);
        }
    }, []);

    const handleLogin = () => {
        if (!window.Kakao) {
            console.error('Kakao SDK가 로드되지 않았습니다.');
            return;
        }

        window.Kakao.Auth.login({
            scope: 'profile_nickname, account_email', // 필요한 동의 항목 설정
            success: (authObj: any) => {
                console.log('액세스 토큰:', authObj.access_token);
                fetchUserInfo(authObj.access_token);
            },
            fail: (err: any) => {
                console.error('로그인 실패:', err);
            },
        });
    };

    const fetchUserInfo = (accessToken: string) => {
        window.Kakao.API.request({
            url: '/v2/user/me',
            success: (res: any) => {
                const kakaoAccount = res.kakao_account;
                console.log('사용자 정보:', kakaoAccount);
                // 여기서 사용자 정보를 상태로 관리하거나 백엔드로 전송할 수 있습니다.
            },
            fail: (err: any) => {
                console.error('사용자 정보 요청 실패:', err);
            },
        });
    };

    return (
        <button onClick={handleLogin}>카카오 로그인</button>
    );
}

export default KakaoLoginType3;