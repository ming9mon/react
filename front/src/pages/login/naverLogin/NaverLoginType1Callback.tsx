import { useEffect, useState } from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';

const NaverLoginType1Callback: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const getNaverToken = () => {
            const hash = location.hash;
            console.log(hash)
            if (!hash) return;

            const token = new URLSearchParams(hash.substring(1));
            const accessToken = token.get('access_token');
            if (accessToken) {
                // 서버로 토큰 전송하여 사용자 정보 처리
                axios
                    .post('/api/login/naver', { token: accessToken })
                    .then((response) => {
                        // 로그인 성공 시 처리 로직
                        //navigate('/'); // 홈 페이지로 리디렉션
                        console.log(response)
                    })
                    .catch((error) => {
                        console.log(error)
                        setError('로그인 처리 중 오류가 발생하였습니다.');
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            } else {
                setError('네이버 로그인에 실패하였습니다.');
                setLoading(false);
            }
        };

        getNaverToken();
    }, [location, navigate]);

    if (loading) {
        return <div>로그인 처리 중...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return null;
};

export default NaverLoginType1Callback;