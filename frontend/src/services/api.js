import { getGlobalShowError } from "@/context/ErrorContext";

const BASE_URL = "http://localhost:8080/api/v1";

async function fetchWith(method, path, body = null, token = null) {
  const options = {
    method,
    headers: { "Content-Type": "application/json" },
  };
  if (body) options.body = JSON.stringify(body);
  if (token) options.headers.Authorization = `Bearer ${token}`;

  try {
    const response = await fetch(`${BASE_URL}${path}`, options);

    // DELETE gibt 204 No Content zurück (kein Body)
    if (response.status === 204) {
      return { data: null, error: null };
    }

    const json = await response.json();

    if (!response.ok) {
      const message = json.message || `Fehler ${response.status}`;

      // 401: Nicht eingeloggt → ab zum Login
      if (response.status === 401) {
        window.location.href = "/login";
        return { data: null, error: json };
      }

      // 400: Validierung → kein Toast, Error geht an die Page
      if (response.status === 400 || response.status === 409) {
        return { data: null, error: json };
      }

      // 403, 404, alles andere → Toast
      getGlobalShowError()?.(message);
      return { data: null, error: json };
    }

    return { data: json, error: null };
  } catch (err) {
    getGlobalShowError()?.("Server nicht erreichbar");
    return { data: null, error: { message: "Server nicht erreichbar" } };
  }
}

const api = {
  get: (path, token) => fetchWith("GET", path, null, token),
  post: (path, body, token) => fetchWith("POST", path, body, token),
  put: (path, body, token) => fetchWith("PUT", path, body, token),
  patch: (path, body, token) => fetchWith("PATCH", path, body, token),
  delete: (path, token) => fetchWith("DELETE", path, null, token),
};

export default api;
