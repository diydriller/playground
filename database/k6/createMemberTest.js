import http from 'k6/http';

export const options = {
    scenarios: {
        createMemberTest: {
            executor: 'constant-arrival-rate',
            rate: 100,
            timeUnit: '1s',
            duration: '2m',
            preAllocatedVUs: 200,
            maxVUs: 500,
        }
    }
};

export default function () {
    const id = `${__VU}_${__ITER}`;
    const memberEmail = `member_${id}@example.com`;
    const createMemberUrl = 'http://127.0.0.1:8080/member';
    const createMemberPayload = JSON.stringify({
        name: 'test',
        email: memberEmail,
        teamId: 1,
    });
    const createMemberParams = {
        headers: {'Content-Type': 'application/json'},
    };
    http.post(createMemberUrl, createMemberPayload, createMemberParams);
}