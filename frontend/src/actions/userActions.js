"use server";

import { z } from "zod";
import UsersApi from "@/lib/api/Users";
import { createSession, verifySession, deleteSession } from "@/lib/session";
import { redirect } from "next/navigation";

// Schema for validating the data of the fields from the form.
const loginSchema = z.object({
  username: z.email("Bitte gebe eine valide E-Mail ein."),
  password: z.string().trim().min(1, "Passwort darf nicht leer sein."),
});

/**
 * Login function
 */
export async function loginAction(prevState, formData) {
  const data = Object.fromEntries(formData);
  const validated = loginSchema.safeParse(data);

  // remove password from data to avoid sending it back to the client. User should re-enter it.
  delete data.password;

  if (!validated.success) {
    return {
      url: prevState?.url,
      fields: data,
      errors: validated.error.flatten().fieldErrors,
      success: false,
    };
  }

  try {
    console.log("LOG IN USER");
    console.log(validated.data);
    const data = await UsersApi.login(validated.data); // Send a POST request to the API to log in the user
    console.log("RESPONSE:");
    console.log(data);
    await createSession(data.accessToken); // Create a new session, storing the token in a cookie
  } catch (error) {
    let errorMessage = "An unexpected error occurred.";

    // Evaluate 4xx client errors
    if (error.response >= 400 && error.response < 500) {
      errorMessage = "Falsche E-Mail oder Passwort. Bitte versuche es erneut.";
    }
    // Evaluate 5xx server errors
    else if (error.response >= 500) {
      errorMessage =
        "Technischer Fehler während dem Login. Bitte versuche es später erneut.";
    }

    return {
      success: false,
      url: prevState?.url,
      data,
      message: errorMessage,
    };
  }
  // Redirect needs to be outside of try catch
  redirect(prevState?.url ?? "/"); // Redirect the user to the page they were originally trying to access.
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
