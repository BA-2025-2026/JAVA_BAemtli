"use client";

import { loginAction } from "@/actions/userActions";
import { useActionState } from "react";
import styles from "@/app/login/page.module.css";

export default function LoginForm() {
  const [state, formAction, isPending] = useActionState(loginAction, {
    success: false,
  });

  return (
    <form action={formAction} className={styles.loginForm} noValidate>
      <input
        name="username"
        autoFocus
        placeholder="E-Mail"
        defaultValue={state?.fields?.username ?? ""}
      />
      {state?.errors?.username && (
        <p className={styles.errorMessage}>{state.errors.username[0]}</p>
      )}
      <input
        name="password"
        type="password"
        placeholder="Passwort"
        defaultValue={state?.fields?.email ?? ""}
      />
      {state?.errors?.password && (
        <p className={styles.errorMessage}>{state.errors.password[0]}</p>
      )}
      <button type="submit" disabled={isPending}>
        {isPending ? "Loading" : "Einloggen"}
      </button>
      {state?.message && <p className={styles.errorMessage}>{state.message}</p>}
    </form>
  );
}
