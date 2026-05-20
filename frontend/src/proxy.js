import { NextResponse } from "next/server";
import { verifySession } from "./lib/session";

/*
 * Proxy (formerly called 'Middleware') protects all routes, except Server Actions.
 * This allows to only access this app if you're logged in.
 * Excemptions are defined in publicRoutes
 */

// Pfade, die NICHT geschützt werden sollen
const publicRoutes = ["/login", "/register", "/favicon.ico"];

export async function proxy(request) {
  const { pathname } = request.nextUrl;

  // Allow public paths without checking
  if (publicRoutes.includes(pathname)) {
    return NextResponse.next();
  }

  // If no session exists, redirect to /login
  const session = await verifySession();

  if (!session) {
    const loginUrl = new URL("/login", request.url);

    // Save url the unauthenticated user tried to access in order to redirect after login
    const fullCallbackUrl = request.nextUrl.pathname + request.nextUrl.search;
    loginUrl.searchParams.set("callbackUrl", fullCallbackUrl);

    // Redirect
    return NextResponse.redirect(loginUrl);
  }

  // If session exists, allow access
  return NextResponse.next();
}

// Define path for wich middleware should be active
export const config = {
  /*
   * Matcher fängt alle Pfade ab, ausser:
   * - _next/static (statische Dateien)
   * - _next/image (Bildoptimierung)
   * - alle Dateien mit Endung (.png, .jpg, jpeg, svg)
   */
  matcher: ["/((?!_next/static|_next/image|.*\\.(?:png|jpg|jpeg|svg)$).*)"],
};
