import axios from "axios";

axios.defaults.baseURL ='http://localhost:8080'

axios.interceptors.request.use(
    (config) => {
        const token = window.localStorage.getItem("token");
        if (token) config.headers.Authorization = token;
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use((config) => {
    const token = config.headers.authorization;
    const role = config.headers.authorities;
    if (role && role.includes('ROLE_ADMIN')){
        window.localStorage.setItem('role', 'ROLE_ADMIN')
    }

    if (role && role.includes('ROLE_USER')){
        window.localStorage.setItem('role', 'ROLE_USER')
    }

    if (token){
        window.localStorage.setItem('token', token)
        window.localStorage.setItem('userId', config.headers.userid)
    }
    return config
})

const requests = {
    get: (url) => axios.get(url).then((res) => res.data),
    post: (url, body) => axios.post(url, body).then((res) => res.data),
    put: (url, body) => axios.put(url, body).then((res) => res.data),
    del: (url) => axios.delete(url).then((res) => res.data),
}

const User = {
    banUser: (id) => requests.put(`/user/${id}/ban`),
    getAll: () => requests.get('/user'),
    create: (user) => requests.post('/user', user),
    details: (id) => requests.get(`/user/${id}`),
    login: (body) => requests.post('/user/login', body),
    update: (body, userId) => requests.put(`user/${userId}`, body),

}

const Poll = {
    getAll: () => requests.get('/poll'),
    delete: (id) => requests.del(`/poll/${id}`),
    create: (userId, body) => requests.post(`/poll/user/${userId}`, body),
    details: (pollId) => requests.get(`/poll/${pollId}`),
}

const Vote = {
    VoteAsRegisteredUser: (pollId, userId, body) => requests.post(`/vote/poll/${pollId}/user/${userId}`, body),
    addVoteByUnregisteredUserToPoll: (pollId, body) => requests.post(`/vote/poll/${pollId}`, body),
}

const IOT = {
    getAllUnpairedDevices: () => requests.get('/device/unpaired'),
    pairPollAndDevice: (deviceId, pollId) => requests.put(`/device/${deviceId}/poll/${pollId}`)
}

export default {
    User,
    Poll,
    Vote,
    IOT
}
