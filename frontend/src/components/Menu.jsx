import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Menu() {

    const [tipo, setTipo] = useState(null);
    const [usuario, setUsuario] = useState("Usuário");
    const [menuAtivo, setMenuAtivo] = useState(false);
    const [dropdownAtivo, setDropdownAtivo] = useState(false);

    const perfilRef = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/api/session", { withCredentials: true })
            .then(res => {
                setTipo(res.data.tipo);
                setUsuario(res.data.usuario?.login || "Usuário");
            })
            .catch(() => setTipo("NAO_LOGADO"));
    }, []);

    function handleMenuClick(sectionId) {
        setMenuAtivo(false);
        if (window.location.pathname === "/" || window.location.pathname === "/index") {
            if (sectionId === "hero") {
                window.scrollTo({ top: 0, behavior: "smooth" });
            } else {
                const section = document.getElementById(sectionId);
                if (section) {
                    section.scrollIntoView({ behavior: "smooth", block: "start" });
                }
            }
        } else {
            navigate("/", { replace: false });
            setTimeout(() => setMenuAtivo(false), 50);
        }
    }

    function toggleMenu() {
        setMenuAtivo(!menuAtivo);
    }

    function toggleDropdown() {
        setDropdownAtivo(!dropdownAtivo);
    }

    async function handleLogout() {
        try {
            await axios.get("http://localhost:8080/api/logout", { withCredentials: true });
            navigate("/");
        } catch (err) {
            console.error("Erro ao deslogar:", err);
        }
    }

    useEffect(() => {
        function handleClickOutside(e) {
            if (perfilRef.current && !perfilRef.current.contains(e.target)) {
                setDropdownAtivo(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <header className="header">
            <section>
                <a href="/" className="logo">
                    <span style={{ color: '#d6a85c', fontWeight: 'bold', fontSize: '1.5rem' }}>
                        SEGURANÇA
                    </span>
                </a>

                <nav className={`navbar ${menuAtivo ? "active" : ""}`}>
                    <a onClick={() => handleMenuClick("hero")}>Início</a>
                    <a onClick={() => handleMenuClick("about")}>Sobre</a>
                    <a onClick={() => handleMenuClick("contato")}>Contato</a>

                    {tipo === "vigilante" && <a href="/ocorrencia">Ocorrência</a>}
                    {tipo === "vigilante" && <a href="/rondas">Rondas</a>}
                    {tipo === "vigilante" && <a href="/monitoramento">Monitoramento</a>}
                    {tipo === "admin" && <a href="/dashboard">Admin</a>}
                </nav>

                <div className="menu-toggle" onClick={toggleMenu}>
                    <i className="fas fa-bars"></i>
                </div>

                <div className="perfil" onClick={toggleDropdown} ref={perfilRef}>
                    <i className="fas fa-user-circle"></i>
                    <span id="user-name">{usuario}</span>

                    <div className={`dropdown-menu ${dropdownAtivo ? "show" : ""}`}>
                        <a href="#">Meu Perfil</a>
                        <a href="#">Configurações</a>
                        <button onClick={handleLogout} className="logout-btn">Sair</button>
                    </div>
                </div>
            </section>
        </header>
    );
}
