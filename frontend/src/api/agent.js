import axios from "axios";

axios.defaults.baseURL ='http://localhost:8080'

const responseBody = (response) => response.data;

axios.interceptors.response.use((config) => {
    console.log(config)
})

const requests = {
    get: (url) => axios.get(url).then(responseBody),
    post: (url, body) => axios.post(url, body).then(responseBody),
    put: (url, body) => axios.put(url, body).then(responseBody),
    del: (url) => axios.delete(url).then(responseBody),
}

const User = {
    create: (user) => requests.post('/user', user),
    details: (id) => requests.get(`/user/${id}`),
    login: (body) => requests.post('/user/login', body)
}

const Poll = {
    create: (userId, body) => requests.post(`/poll/user/${userId}`, body),
    details: (pollId) => requests.get(`/poll/${pollId}`),
}

export default {
    User,
    Poll
}
