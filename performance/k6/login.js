import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 5,
    duration: '30s',
    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<5000'],
    },
};

const BASE_URL = 'https://opensource-demo.orangehrmlive.com';

export default function () {
    const response = http.get(`${BASE_URL}/web/index.php/auth/login`);

    check(response, {
        'login page status is 200': (r) => r.status === 200,
        'login page loaded': (r) =>
            r.body && r.body.includes('OrangeHRM'),
    });

    sleep(1);
}
