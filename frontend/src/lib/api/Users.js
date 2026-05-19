import { BASE_URL, postJSON, getJSON } from ".";

const URL = BASE_URL;

const UsersApi = {
  login(user) {
    return postJSON(`${URL}/login`, user);
  },
  read(id, accessToken) {
    return getJSON(`${URL}/users/${id}`, accessToken);
  },
};

export default UsersApi;
