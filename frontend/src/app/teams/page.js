"use client";
import styles from "./page.module.css";
import { useState } from "react";

export default function Teams() {
  const testTeams = [
    { id: 1, name: "PUNCS" },
    { id: 2, name: "Plapplis" },
    { id: 3, name: "Beeliveries" },
  ];
  const [teams, setTeams] = useState(testTeams);
  const [isAddingTeam, setIsAddingTeam] = useState(false);
  const [addTeamValue, setAddTeamText] = useState("");

  const addTeam = () => {
    const newTeam = {
      id: crypto.randomUUID(),
      name: addTeamValue,
    };
    setTeams([...teams, newTeam]);
    setAddTeamText("");
    setIsAddingTeam(false);
  };

  const deleteTeam = (id) => {
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
        <div className={`${styles.teamTitleCard} ${styles.addTeamSection}`}>
          <input
            type="text"
            onChange={(e) => setAddTeamText(e.target.value)}
            value={addTeamValue}
          />
          <button onClick={addTeam}>Team erstellen</button>
          <button onClick={() => setIsAddingTeam(false)}>Abbrechen</button>
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
