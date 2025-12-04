import { useEffect, useState } from "react";
import axios from "axios";
import Menu from "../components/Menu";
import { Helmet } from "react-helmet";
import "../style/css/styleDashboard.css";
import { 
  ShieldCheck, 
  AlertCircle, 
  AlarmClock, 
  Inbox, 
  Users, 
  Shield 
} from "lucide-react";

export default function DashboardAdmin() {
  const [dados, setDados] = useState(null);
  const [secao, setSecao] = useState(null);
  const [modal, setModal] = useState({ tipo: null, id: null, acao: null });

  useEffect(() => {
    carregarDashboard();
  }, []);

  const carregarDashboard = () => {
    axios
      .get("http://localhost:8080/api/dashboard", { withCredentials: true })
      .then((res) => setDados(res.data))
      .catch((err) => console.error("Erro ao carregar dashboard:", err));
  };

  if (!dados) return <div>Carregando...</div>;

  const abrirModal = (tipo, id = null, acao = "editar") =>
    setModal({ tipo, id, acao });

  const fecharModal = () => setModal({ tipo: null, id: null, acao: null });

  const mostrarSecao = (sec) => setSecao(sec);

  return (
    <>
      <Helmet>
        <title>Dashboard Admin</title>
      </Helmet>

      <Menu />

      <div className="dashboard-container">
        <header className="dashboard-header">
          <h1>Dashboard do Administrador</h1>
          <p>Bem-vindo à central de comando</p>
        </header>

        <div className="dashboard-indicators">
          <Card titulo="Rondas" valor={dados.rondas.length} icon={<ShieldCheck size={32} />} />
          <Card titulo="Ocorrências" valor={dados.ocorrencias.length} icon={<AlertCircle size={32} />} />
          <Card titulo="Alarmes" valor={dados.alarmes.length} icon={<AlarmClock size={32} />} />
          <Card titulo="Relatos" valor={dados.gravacoes.length} icon={<Inbox size={32} />} />
          <Card titulo="Clientes" valor={dados.clientes.length} icon={<Users size={32} />} />
          <Card titulo="Vigilantes" valor={dados.vigilantes.length} icon={<Shield size={32} />} />
        </div>

        <section className="dashboard-shortcuts">
          <h2>Atalhos Rápidos</h2>
          <div className="shortcut-grid">
            <button onClick={() => mostrarSecao("rondas")}>Ver Rondas</button>
            <button onClick={() => mostrarSecao("ocorrencias")}>Ver Ocorrências</button>
            <button onClick={() => mostrarSecao("alarmes")}>Ver Alarmes</button>
            <button onClick={() => mostrarSecao("gravacoes")}>Ver Gravações</button>
            <button onClick={() => mostrarSecao("vigilantes")}>Ver Vigilantes</button>
            <button onClick={() => mostrarSecao("clientes")}>Ver Clientes</button>
          </div>
        </section>

        {secao === "rondas" && <TabelaRondas dados={dados.rondas} abrirModal={abrirModal} setDados={setDados} />}
        {secao === "ocorrencias" && <TabelaOcorrencias dados={dados.ocorrencias} abrirModal={abrirModal} setDados={setDados} />}
        {secao === "alarmes" && <TabelaAlarmes dados={dados.alarmes} abrirModal={abrirModal} setDados={setDados} />}
        {secao === "gravacoes" && <TabelaGravacoes dados={dados.gravacoes} abrirModal={abrirModal} setDados={setDados} />}
        {secao === "vigilantes" && <TabelaVigilantes dados={dados.vigilantes} abrirModal={abrirModal} setDados={setDados} />}
        {secao === "clientes" && <TabelaClientes dados={dados.clientes} abrirModal={abrirModal} setDados={setDados} />}

        {modal.tipo && (
          <Modal
            tipo={modal.tipo}
            id={modal.id}
            acao={modal.acao}
            dados={dados}
            fecharModal={fecharModal}
            recarregar={carregarDashboard}
          />
        )}
      </div>
    </>
  );
}

/* COMPONENTES */
function Card({ titulo, valor, icon }) {
  return (
    <div className="card">
      {icon}
      <h3>{titulo}</h3>
      <p>{valor}</p>
    </div>
  );
}

/* FUNCAO GLOBAL DE DELETE */
async function deletar(endpoint, param, dados, setDados) {
  if (!window.confirm("Deseja realmente excluir?")) return;

  try {
    await axios.delete(
      `http://localhost:8080/api/${endpoint}/excluir/${param}`,
      { withCredentials: true }
    );

    // Mapa de campos únicos por tabela
    const campoId = {
      rondas: "nome",
      ocorrencias: "id",
      alarmes: "id",
      gravacoes: "id",
      vigilantes: "login",
      clientes: "cpf"
    };

    setDados((prevDados) => {
      // Pega a chave do estado correspondente ao endpoint
      const chave = endpoint.split("/").pop(); 
      if (!prevDados[chave]) return prevDados;

      const campo = campoId[chave] || "id";

      return {
        ...prevDados,
        [chave]: prevDados[chave].filter(item => item[campo] !== param)
      };
    });
  } catch (err) {
    console.error("Erro ao excluir:", err);
  }
}



/* TABELAS */
function TabelaRondas({ dados, abrirModal, setDados }) {
  return (
    <div className="tabela-container">
      <h2>Rondas</h2>
      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>Bairro</th>
            <th>Descrição</th>
            <th>Observação</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((r) => (
            <tr key={r.nome}>
              <td>{r.nome}</td>
              <td>{r.bairro}</td>
              <td>{r.descricao}</td>
              <td>{r.observacao}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("rondas", r.nome)}>Editar</button>
                  <button className="delete" onClick={() => deletar("admin/rondas", r.nome, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("rondas", null, "novo")}>Nova Ronda</button>
    </div>
  );
}

function TabelaOcorrencias({ dados, abrirModal, setDados  }) {
  return (
    <div className="tabela-container">
      <h2>Ocorrências</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Data</th>
            <th>Duração</th>
            <th>Vigilante</th>
            <th>Produto</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((o) => (
            <tr key={o.id}>
              <td>{o.id}</td>
              <td>{o.data}</td>
              <td>{o.duracao}</td>
              <td>{o.idVigilante}</td>
              <td>{o.idProduto}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("ocorrencias", o.id)}>Editar</button>
                  <button className="delete" onClick={() => deletar("admin/ocorrencias", o.id, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("ocorrencias", null, "novo")}>Nova Ocorrência</button>
    </div>
  );
}

function TabelaAlarmes({ dados, abrirModal, setDados  }) {
  return (
    <div className="tabela-container">
      <h2>Alarmes</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Instalação</th>
            <th>Retirada</th>
            <th>Defeito</th>
            <th>ID Endereço</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((a) => (
            <tr key={a.id}>
              <td>{a.id}</td>
              <td>{a.dataInst}</td>
              <td>{a.dataRet}</td>
              <td>{a.defeito ? "Sim" : "Não"}</td>
              <td>{a.idEndereco}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("alarmes", a.id)}>Editar</button>
                  <button className="delete" onClick={() => deletar("produtos", a.id, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("alarmes", null, "novo")}>Novo Alarme</button>
    </div>
  );
}

function TabelaGravacoes({ dados, abrirModal, setDados  }) {
  return (
    <div className="tabela-container">
      <h2>Gravações</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Descrição</th>
            <th>Duração</th>
            <th>Data</th>
            <th>Arquivo</th>
            <th>Produto</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((g) => (
            <tr key={g.id}>
              <td>{g.id}</td>
              <td>{g.descricao}</td>
              <td>{g.duracao}</td>
              <td>{g.data}</td>
              <td>{g.arquivo}</td>
              <td>{g.idProduto}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("gravacoes", g.id)}>Editar</button>
                  <button className="delete" onClick={() => deletar("gravacoes", g.id, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("gravacoes", null, "novo")}>Nova Gravação</button>
    </div>
  );
}

function TabelaVigilantes({ dados, abrirModal, setDados  }) {
  return (
    <div className="tabela-container">
      <h2>Vigilantes</h2>
      <table>
        <thead>
          <tr>
            <th>Login</th>
            <th>Turno</th>
            <th>Carga Horária</th>
            <th>Remuneração</th>
            <th>Email</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((v) => (
            <tr key={v.login}>
              <td>{v.login}</td>
              <td>{v.turno}</td>
              <td>{v.cargaHoraria}</td>
              <td>{v.remuneracao}</td>
              <td>{v.contatoInfo?.email}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("vigilantes", v.login)}>Editar</button>
                  <button className="delete" onClick={() => deletar("admin/vigilantes", v.login, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("vigilantes", null, "novo")}>Novo Vigilante</button>
    </div>
  );
}

function TabelaClientes({ dados, abrirModal, setDados  }) {
  return (
    <div className="tabela-container">
      <h2>Clientes</h2>
      <table>
        <thead>
          <tr>
            <th>Login</th>
            <th>CPF</th>
            <th>Data Nasc</th>
            <th>Telefone</th>
            <th>Email</th>
            <th>Contato</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {dados.map((c) => (
            <tr key={c.cpf}>
              <td>{c.login}</td>
              <td>{c.cpf}</td>
              <td>{c.dataNasc}</td>
              <td>{c.contatoInfo?.fone}</td>
              <td>{c.contatoInfo?.email}</td>
              <td>{c.contatoInfo?.foneContato}</td>
              <td>
                <div className="acoes-botoes">
                  <button className="edit" onClick={() => abrirModal("clientes", c.login)}>Editar</button>
                  <button className="delete" onClick={() => deletar("clientes", c.cpf, dados, setDados)}>Excluir</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => abrirModal("clientes", null, "novo")}>Novo Cliente</button>
    </div>
  );
}


/* MODAL GENERICO */
function Modal({ tipo, id, acao, dados, fecharModal, recarregar }) {
  const [form, setForm] = useState({});
  const [erros, setErros] = useState({});

  const camposPorTipo = {
    rondas: [
      { nome: "nome", ph: "Nome da ronda" },
      { nome: "bairro", ph: "Ex: Centro" },
      { nome: "descricao", ph: "Descrição detalhada" },
      { nome: "observacao", ph: "Observações gerais" }
    ],
    ocorrencias: [
      { nome: "data", ph: "dd/mm/yyyy", tipo: "data" },
      { nome: "duracao", ph: "HH:mm:ss", tipo: "hora" },
      { nome: "idVigilante", ph: "Login do vigilante" },
      { nome: "idProduto", ph: "ID do produto" }
    ],
    alarmes: [
      { nome: "dataInst", ph: "dd/mm/yyyy", tipo: "data" },
      { nome: "dataRet", ph: "dd/mm/yyyy", tipo: "data" },
      { nome: "defeito", ph: "" },
      { nome: "idEndereco", ph: "ID do endereço" }
    ],
    gravacoes: [
      { nome: "descricao", ph: "Descrição da gravação" },
      { nome: "duracao", ph: "HH:mm:ss", tipo: "hora" },
      { nome: "data", ph: "dd/mm/yyyy", tipo: "data" },
      { nome: "arquivo", ph: "Nome do arquivo" },
      { nome: "idProduto", ph: "ID do produto" }
    ],
    vigilantes: [
      { nome: "login", ph: "Login do vigilante" },
      { nome: "senha", ph: "Senha de acesso" },
      { nome: "turno", ph: "D/N" },
      { nome: "cargaHoraria", ph: "HH:mm:ss", tipo: "hora" },
      { nome: "remuneracao", ph: "Valor em R$" },
      { nome: "dataContratacao", ph: "dd/MM/yyyy", tipo: "data" },
      { nome: "fone", ph: "(xx) xxxxx-xxxx" },
      { nome: "email", ph: "email@email.com" },
      { nome: "foneContato", ph: "(xx) xxxxx-xxxx" }
    ],
    clientes: [
      { nome: "login", ph: "Login do cliente" },
      { nome: "senha", ph: "Senha de acesso" },
      { nome: "cpf", ph: "CPF do cliente" },
      { nome: "dataNasc", ph: "dd/MM/yyyy", tipo: "data" },
      { nome: "fone", ph: "(xx) xxxxx-xxxx" },
      { nome: "email", ph: "email@email.com" },
      { nome: "foneContato", ph: "(xx) xxxxx-xxxx" }
    ]
  };

  const endpoint = {
    rondas: "admin/rondas",
    ocorrencias: "admin/ocorrencias",
    alarmes: "produtos",
    gravacoes: "gravacoes",
    vigilantes: "admin/vigilantes",
    clientes: "clientes"
  };

  const regexData = /^\d{2}\/\d{2}\/\d{4}$/;
  const regexHora = /^\d{2}:\d{2}:\d{2}$/;

  useEffect(() => {
    let item =
      acao === "editar"
        ? dados[tipo]?.find(
            (d) => d.id === id || d.login === id || d.nome === id
          ) || {}
        : {};

    if (acao === "novo") {
      camposPorTipo[tipo].forEach((c) => (item[c.nome] = ""));
    }

    //Corrige turno para "D" ou "N"
    if (tipo === "vigilantes" && item.turno) {
      if (item.turno === "Diurno") item.turno = "D";
      if (item.turno === "Noturno") item.turno = "N";
      if (item.turno.descricao === "Diurno") item.turno = "D";
      if (item.turno.descricao === "Noturno") item.turno = "N";
    }

    //Extrai contatoInfo
    if (tipo === "vigilantes" && item.contatoInfo) {
      item.fone = item.contatoInfo.fone || "";
      item.email = item.contatoInfo.email || "";
      item.foneContato = item.contatoInfo.foneContato || "";
    }

    //Converte dataContratacao do backend YYYY-MM-DD - dd/MM/yyyy
    if (tipo === "vigilantes" && item.dataContratacao) {
      if (item.dataContratacao.includes("-")) {
        const partes = item.dataContratacao.split("-");
        item.dataContratacao = `${partes[2]}/${partes[1]}/${partes[0]}`;
      }
    }

    //Converte cargaHoraria HH:mm - HH:mm:ss
    if (tipo === "vigilantes" && item.cargaHoraria) {
      if (item.cargaHoraria.length === 5) {
        item.cargaHoraria = item.cargaHoraria + ":00";
      }
    }

    setForm(item);
  }, [tipo, id, acao, dados]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });

    const campoInfo = camposPorTipo[tipo].find((c) => c.nome === name);
    if (!campoInfo) return;

    if (campoInfo.tipo === "data" && !regexData.test(value)) {
      setErros({ ...erros, [name]: "Data inválida (use dd/MM/yyyy)" });
    } else if (campoInfo.tipo === "hora" && !regexHora.test(value)) {
      setErros({ ...erros, [name]: "Hora inválida (use HH:mm:ss)" });
    } else {
      const novosErros = { ...erros };
      delete novosErros[name];
      setErros(novosErros);
    }
  };

  const salvar = async (e) => {
    e.preventDefault();

    if (Object.keys(erros).length > 0) {
      alert("Corrija os campos marcados em vermelho antes de salvar.");
      return;
    }

    let payload = form;

    //Monta JSON correto para vigilantes
    if (tipo === "vigilantes") {
      payload = {
        login: form.login,
        senha: form.senha,
        turno: form.turno,
        cargaHoraria: form.cargaHoraria,
        remuneracao: Number(form.remuneracao),
        dataContratacao: form.dataContratacao,
        contatoInfo: {
          fone: form.fone,
          email: form.email,
          foneContato: form.foneContato
        }
      };
    }

    //Monta JSON correto para clientes
    if (tipo === "clientes") {
      payload = {
        login: form.login,
        senha: form.senha,
        cpf: form.cpf,
        dataNasc: form.dataNasc,
        contatoInfo: {
          fone: form.fone,
          email: form.email,
          foneContato: form.foneContato
        }
      };
    }

    try {
      await axios.post(
        `http://localhost:8080/api/${endpoint[tipo]}/salvar`,
        payload,
        { withCredentials: true }
      );
      recarregar();
      fecharModal();
    } catch (err) {
      console.error("Erro ao salvar:", err);
    }
  };

  return (
    <div className="modal" style={{ display: "flex" }}>
      <div className="modal-content">
        <span className="close" onClick={fecharModal}>&times;</span>

        <h2>{acao === "editar" ? "Editar" : "Novo"} {tipo}</h2>

        <form onSubmit={salvar}>
          {camposPorTipo[tipo].map((campo) => (
            <div key={campo.nome}>
              <label>{campo.nome}:</label>

              {campo.nome === "defeito" ? (
                <select
                  name="defeito"
                  value={form.defeito ? "Sim" : "Não"}
                  onChange={(e) =>
                    setForm({ ...form, defeito: e.target.value === "Sim" })
                  }
                >
                  <option value="Não">Não</option>
                  <option value="Sim">Sim</option>
                </select>
              ) : campo.nome === "turno" ? (
                <select
                  name="turno"
                  value={form.turno || ""}
                  onChange={handleChange}
                >
                  <option value="">Selecione...</option>
                  <option value="D">Diurno</option>
                  <option value="N">Noturno</option>
                </select>
              ) : (
                <input
                  name={campo.nome}
                  placeholder={campo.ph}
                  value={form[campo.nome] ?? ""}
                  onChange={handleChange}
                  style={{
                    border: erros[campo.nome]
                      ? "2px solid red"
                      : "1px solid #ccc"
                  }}
                />
              )}

              {erros[campo.nome] && (
                <p style={{ color: "red", fontSize: "12px" }}>
                  {erros[campo.nome]}
                </p>
              )}
            </div>
          ))}

          <button type="submit">Salvar</button>
        </form>
      </div>
    </div>
  );
}

