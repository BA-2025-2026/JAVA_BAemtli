"use client";

import { useEffect, useState } from "react";

export default function Home() {
    const [teams, setTeams] = useState<any[]>([]);
    const [newTeamName, setNewTeamName] = useState("");
    const [status, setStatus] = useState("");

    // 1. Funktion zum Laden der Daten (GET)
    const fetchTeams = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/v1/teams");
            if (!res.ok) throw new Error("Fehler beim Laden");
            const data = await res.json();
            setTeams(data);
        } catch (err) {
            console.error(err);
        }
    };

    // Initiales Laden
    useEffect(() => {
        fetchTeams();
    }, []);

    // 2. Funktion zum Senden der Daten (POST)
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Validierung: Mindestens 1 Buchstabe
        if (newTeamName.trim().length === 0) {
            setStatus("Bitte einen Namen eingeben!");
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/api/v1/teams", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                // Das Backend erwartet ein CreateTeamDTO mit dem Feld 'name'
                body: JSON.stringify({ name: newTeamName }),
            });

            if (res.status === 201) {
                setStatus("Team erfolgreich erstellt!");
                setNewTeamName(""); // Empty list
                fetchTeams(); // Refresh team list
            } else if (res.status === 403) {
                setStatus("Zugriff verweigert (kein JWT Token?)");
            } else {
                setStatus("Fehler: " + res.status);
            }
        } catch (err) {
            setStatus("Netzwerkfehler");
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center p-24 bg-gray-900 text-white">
            <h1 className="text-3xl font-bold mb-10 text-blue-400">Team Management</h1>

            {/* Formular zum Erstellen */}
            <form onSubmit={handleSubmit} className="mb-10 flex flex-col gap-4 bg-gray-800 p-6 rounded-lg shadow-md">
                <input
                    type="text"
                    value={newTeamName}
                    onChange={(e) => setNewTeamName(e.target.value)}
                    placeholder="Team Name eingeben..."
                    className="p-2 rounded bg-gray-700 border border-gray-600 focus:outline-none focus:border-blue-500"
                />
                <button
                    type="submit"
                    className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded transition"
                >
                    Team erstellen
                </button>
                {status && <p className="text-sm font-mono text-yellow-400">{status}</p>}
            </form>

            {/* Anzeige der Liste */}
            <div className="w-full max-w-md">
                <h2 className="text-xl mb-4 border-b border-gray-700 pb-2">
                    Gefundene Teams: <span className="text-blue-400">{teams.length}</span>
                </h2>
                <ul className="space-y-2">
                    {teams.map((team) => (
                        <li key={team.id} className="bg-gray-800 p-3 rounded border border-gray-700">
                            {team.name} <span className="text-gray-500 text-xs">(ID: {team.id})</span>
                        </li>
                    ))}
                </ul>
            </div>
        </main>
    );
}