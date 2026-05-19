"use server";

import { z } from "zod";
import UsersApi from "@/lib/api/Users";
import { createSession, verifySession, deleteSession } from "@/lib/session";
import { redirect } from "next/navigation";
import { revalidatePath } from "next/cache";

// Schema for validating the data of the fields from the form.
const schema = z.object({
  email: z.email("Please enter a valid email address"),
  password: z.string().trim(),
});

/**
 * Login function
 */
export async function loginAction(state, formData) {
  const data = Object.fromEntries(formData);
  const fields = schema.safeParse(data);

  // remove password from data to avoid sending it back to the client. User should re-enter it.
  delete data.password;
  console.log(fields.data);
  if (!fields.success) {
    return {
      url: state?.url,
      data,
      errors: fields.error.flatten().fieldErrors,
    };
  }

  try {
    const data = await UsersApi.login(fields.data); // Send a POST request to the API to log in the user
    await createSession(data.accessToken); // Create a new session, storing the token in a cookie
  } catch (error) {
    return {
      url: state?.url,
      data,
      message:
        error.response === 400
          ? "Invalid email or password. Please try again."
          : "Login failed due to a technical issue. Please try again later.",
    };
  }
  redirect(state?.url ?? "/profile"); // Redirect the user to the page they were originally trying to access.
}

/**
 * A server action that handles user logouts.
 */
export async function logoutAction() {
  await deleteSession();
  redirect("/");
}

/**
 * Get username of the logged in user
 */
export async function getUsernameAction() {
  const session = await verifySession();

  if (!session) return null;

  const user = await UsersApi.read(session.user.id, session.accessToken);
  return user.username;
}

/**
 * Get username of user per id
 */
export async function getUsernamePerIdAction(id) {
  const session = await verifySession();

  if (!session) return null;

  const user = await UsersApi.read(id, session.accessToken);
  return user.username;
}
