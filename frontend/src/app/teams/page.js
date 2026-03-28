"use client";
import styles from "./page.module.css";
import { useState, useEffect } from "react";
import api from "@/services/api";

export default function Teams() {
  const [teams, setTeams] = useState([]);
  const [isAddingTeam, setIsAddingTeam] = useState(false);
  const [addTeamValue, setAddTeamText] = useState("");
  const [fieldError, setFieldError] = useState(null);

  // Load teams
  useEffect(() => {
    const loadTeams = async () => {
      const { data, error } = await api.get("/teams");
      if (error) return;
      setTeams(data);
    };
    loadTeams();
  }, []);

  const addTeam = async () => {
    setFieldError(null);

    // Some validation
    const trimmed = addTeamValue.trim();
    if (!trimmed) {
      setFieldError("Name darf nicht leer sein");
      return;
    }
    if (trimmed.length < 2 || trimmed.length > 30) {
      setFieldError("Name muss zwischen 2 und 30 Zeichen lang sein");
      return;
    }

    const { data, error } = await api.post("/teams", { name: trimmed });

    if (error) {
      // fieldErrors from Backend
      if (error.fieldErrors?.length) {
        setFieldError(error.fieldError[0].error);
      } else {
        setFieldError(error.message);
      }
      return;
    }

    setTeams([...teams, data]);
    setAddTeamText("");
    setFieldError(null);
    setIsAddingTeam(false);
  };

  const deleteTeam = async (id) => {
    const { error } = await api.delete(`/teams/${id}`);
    if (error) return;
    setTeams(teams.filter((team) => team.id !== id));
  };

  return (
    <div>
      <h1>Teams & Lernende</h1>

      {teams.map((team) => (
        <div className={styles.teamTitleCard} key={team.id}>
          <h4>{team.name}</h4>
          <div className={styles.crudButtons}>
            <span className="material-symbols-outlined">edit</span>
            <span
              className="material-symbols-outlined"
              onClick={() => deleteTeam(team.id)}
            >
              delete
            </span>
          </div>
        </div>
      ))}

      {isAddingTeam ? (
        <div>
          <div className={`${styles.teamTitleCard} ${styles.addTeamSection}`}>
            <input
              type="text"
              onChange={(e) => {
                setAddTeamText(e.target.value);
                setFieldError(null); // Clear error when typing
              }}
              value={addTeamValue}
              className={fieldError ? styles.inputError : ""}
              placeholder="Teamname"
            />
            <button onClick={addTeam}>Team erstellen</button>
            <button
              onClick={() => {
                setIsAddingTeam(false);
                setFieldError(null);
              }}
            >
              Abbrechen
            </button>
          </div>
          {fieldError && <p className={styles.fieldErrorText}>{fieldError}</p>}
        </div>
      ) : (
        <div
          className={`${styles.teamTitleCard} ${styles.addTeamSection}`}
          onClick={() => setIsAddingTeam(true)}
        >
          <span className="material-symbols-outlined">add</span>
          <h4>Neues Team anlegen</h4>
        </div>
      )}
    </div>
  );
}
