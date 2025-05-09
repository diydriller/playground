import http from 'k6/http';

export const options = {
    scenarios: {
        updateTeamTest: {
            executor: 'constant-arrival-rate',
            rate: 1000,
            timeUnit: '1s',
            duration: '1m',
            preAllocatedVUs: 5000,
            maxVUs: 10000,
        }
    }
};

export default function () {
    const id = `${__VU}_${__ITER}`;
    const updateTeamUrl = 'http://127.0.0.1:8080/team';
    const updateTeamPayload = JSON.stringify({
        name: `test ${id}`,
        teamId: 1,
    });
    const updateTeamParams = {
        headers: {'Content-Type': 'application/json'},
    };
    http.post(updateTeamUrl, updateTeamPayload, updateTeamParams);
}