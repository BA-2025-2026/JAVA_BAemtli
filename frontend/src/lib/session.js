import { cache } from "react";
import { cookies } from "next/headers";
import { decodeJwt } from "jose";

/**
 * JWT-Utility (Token,.Session)
 */
// We only check if a cookie with a session  accessToken exists and if its contents can be decoded.
// This means that we can cache the response, as we only need to perform this check once per page navigation.
export const verifySession = cache(async () => {
  const cookieStore = await cookies();
  const accessToken = cookieStore.get("session")?.value;

  if (!accessToken) return null;

  const claims = decodeJwt(accessToken);
  return {
    accessToken,
    user: {
      id: claims.sub,
      email: claims.email,
      username: claims.username,
    },
    //scope: claims.scope.split(" "),     // Return the  accessToken (to perform API requests) and the scope claim (to perform optimistic auth checks).
  };
});

/**
 * Creates a session by storing the specified  accessToken in a cookie.
 * @param {string}  accessToken - The session  accessToken
 */
export async function createSession(accessToken) {
  const cookieStore = await cookies();

  // Store the session  accessToken in a "secure" cookie
  cookieStore.set("session", accessToken, {
    httpOnly: true,
    secure: true,
    expires: new Date(Date.now() + 60 * 60 * 1000),
    sameSite: "strict",
    path: "/",
  });
}

/**
 * Invalidates an existing session, removing the corresponding cookie.
 */
export async function deleteSession() {
  const cookieStore = await cookies();
  cookieStore.delete("session");
}

/**
 * Just a helper function to decode a JWT token.
 * @returns
 */
export async function decodeJwtSelfmade(token) {
  try {
    const base64Payload = token.split(".")[1];
    const payload = atob(base64Payload);
    return JSON.parse(payload);
  } catch {
    return null;
  }
}
