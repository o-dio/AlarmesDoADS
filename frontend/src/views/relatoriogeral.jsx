import { useState } from "react";
import axios from "axios";
import { Helmet } from "react-helmet";
import Menu from "../components/Menu";
import "../style/css/relatoriogeral.css";

export default function RelatorioGeral() {
    const [relatorioGerado, setRelatorioGerado] = useState(false);

    async function handleVerRelatorio() {
        try {
            const response = await axios.get("http://localhost:8080/api/relatorio/geral/ver", {
                responseType: "blob", // importante para PDFs
                withCredentials: true
            });

            const file = new Blob([response.data], { type: "application/pdf" });
            const fileURL = URL.createObjectURL(file);
            window.open(fileURL); // abre o PDF em nova aba

            setRelatorioGerado(true);
        } catch (err) {
            console.error("Erro ao gerar relatório:", err);
            alert("Não foi possível gerar o relatório.");
        }
    }

    async function handleBaixarRelatorio() {
        try {
            const response = await axios.get("http://localhost:8080/api/relatorio/geral/baixar", {
                responseType: "blob",
                withCredentials: true
            });

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "relatorio_geral.pdf");
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (err) {
            console.error("Erro ao baixar relatório:", err);
            alert("Não foi possível baixar o relatório.");
        }
    }

    return (
        <>
            <Helmet>
                <title>Relatório Geral</title>
                <meta charSet="UTF-8" />
            </Helmet>

            <Menu />

            <section className="relatorio-container">
                <h1>Relatório Geral</h1>
                <p>Gere e baixe o relatório geral do sistema.</p>

                <div className="buttons">
                    {!relatorioGerado && (
                        <button onClick={handleVerRelatorio}>Ver relatório</button>
                    )}

                    <button 
                        onClick={handleBaixarRelatorio} 
                        style={{ marginTop: relatorioGerado ? "20px" : "0px" }}
                    >
                        Baixar relatório
                    </button>
                </div>
            </section>
        </>
    );
}
