const BASE_URL = "http://localhost:8080/api/v1";

async function fetchWith(method, path, body = null, token = null) {
  const options = {
    method,
    headers: { "Content-Type": "application/json" },
  };
  if (body) options.body = JSON.stringify(body);
  if (token) options.headers.Authorization = `Bearer ${token}`;

  const response = await fetch(`${BASE_URL}${path}`, options);
  if (!response.ok) throw new Error(`Request failed: ${response.status}`);

  // Responses with no JSON body
  if (
    response.status === 204 ||
    response.headers.get("content-length") === "0"
  ) {
    return null;
  }

  // Responses with JSON body
  return response.json();
}

const api = {
  get: (path, token) => fetchWith("GET", path, null, token),
  post: (path, body, token) => fetchWith("POST", path, body, token),
  put: (path, body, token) => fetchWith("PUT", path, body, token),
  delete: (path, token) => fetchWith("DELETE", path, null, token),
};

export default api;
