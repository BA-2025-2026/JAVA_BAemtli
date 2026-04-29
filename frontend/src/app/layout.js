import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";

export const metadata = {
  title: "Ämtliplantool | ICT-Campus",
  description: "Tool zur Verwaltung der Ämtli am ICT-Campus",
};

export default function RootLayout({ children }) {
  return (
    <html lang="de">
      <body>{children}</body>
    </html>
  );
}
