import { Inter } from "next/font/google";
import "./globals.css";
import Sidebar from "@/components/Sidebar/Sidebar";

const inter = Inter({ subsets: ["latin"], weight: ["300", "600"] });

export const metadata = {
  title: "Ämtliplantool",
  description: "Ämtliplantool des ICT-Campus",
};

export default function RootLayout({ children }) {
  return (
    <html lang="de" className={inter.className}>
      <head>
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
        />
      </head>
      <body>{children}</body>
    </html>
  );
}
