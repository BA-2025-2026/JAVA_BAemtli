import LoginForm from "@/components/LoginForm/LoginForm";
import styles from "./page.module.css";

export default function Login() {
  return (
    <div className={styles.loginContainer}>
      <div className={styles.loginCard}>
        <h1>Ämtliplantool</h1>
        <p>des ICT-Campus</p>
        <h4>Willkommen zurück.</h4>

        <LoginForm />
      </div>
    </div>
  );
}
