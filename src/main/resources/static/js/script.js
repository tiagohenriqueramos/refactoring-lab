const API_BASE_URL = 'https://finalizador-encerramento-axdhangvgvhxbuha.canadacentral-01.azurewebsites.net';

// 1. Busca os dados do Pedido no Azure
async function buscarPedido() {
    const id = document.getElementById('inputBuscaId').value.trim();
    if (!id) return alert('Por favor, informe o ID do pedido.');

    try {
        const response = await fetch(`${API_BASE_URL}/v1/pedidos/${id}`);

        if (response.status === 404) {
            alert('Pedido não encontrado no MongoDB!');
            return;
        }

        if (!response.ok) throw new Error(`Status HTTP: ${response.status}`);

        const pedido = await response.json();

        // Preenche o formulário e o painel informativo
        document.getElementById('pedidoEntregaId').value = pedido.id;
        document.getElementById('infoDestinatario').innerText = pedido.nomeDestinatario || 'N/I';
        document.getElementById('infoStatus').innerText = pedido.statusPedido || 'N/I';
        document.getElementById('infoOcorrencia').innerText = pedido.statusUltimaOcorrencia || 'N/I';

        document.getElementById('painelDetalhes').style.display = 'block';

    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Falha ao conectar com o serviço no Azure.');
    }
}

// 2. Submete o encerramento do lote para a API
document.getElementById('formEncerramento').addEventListener('submit', async (e) => {
    e.preventDefault();

    const roteiroId = document.getElementById('roteiroId').value;
    const usuarioId = document.getElementById('usuarioId').value;
    const pedidoEntregaId = document.getElementById('pedidoEntregaId').value;

    if (!pedidoEntregaId) {
        alert('Carregue um pedido válido antes de processar!');
        return;
    }

    const payload = [{
        pedidoEntregaId: pedidoEntregaId,
        novoStatus: document.getElementById('novoStatus').value,
        novoStatusOcorrencia: document.getElementById('novoStatusOcorrencia').value,
        motivoInsucesso: document.getElementById('motivoInsucesso').value
    }];

    try {
        const response = await fetch(`${API_BASE_URL}/encerramento-roteiro/${roteiroId}?usuarioId=${usuarioId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) throw new Error(`Status HTTP: ${response.status}`);

        const resultados = await response.json();
        renderizarResultados(resultados);

    } catch (error) {
        console.error('Erro ao encerrar:', error);
        alert('Erro ao enviar a requisição de encerramento.');
    }
});

// 3. Renderiza o resultado devolvido pelo Use Case
function renderizarResultados(resultados) {
    const tbody = document.getElementById('tabelaResultados');
    tbody.innerHTML = '';

    resultados.forEach(item => {
        const tr = document.createElement('tr');

        const badgeClass = item.possuiErro ? 'badge-danger' : 'badge-success';
        const statusTexto = item.possuiErro ? 'Erro' : 'Sucesso';

        tr.innerHTML = `
            <td>${item.pedidoId || '-'}</td>
            <td><span class="badge ${badgeClass}">${statusTexto}</span></td>
            <td>${item.mensagem || 'Encerrado com sucesso'}</td>
        `;
        tbody.appendChild(tr);
    });

    document.getElementById('cardResultados').style.display = 'block';
}

// Carrega o pedido inicial automaticamente ao abrir
window.addEventListener('DOMContentLoaded', () => {
    buscarPedido();
});

// 4. Busca todos os pedidos e preenche a tabela em formato Excel
async function buscarTodosPedidos() {
    const container = document.getElementById('containerTabelaGeral');
    const tbody = document.getElementById('tabelaGeralCorpo');
    tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--text-sub);">Carregando pedidos...</td></tr>';
    container.style.display = 'block';

    try {
        const response = await fetch(`${API_BASE_URL}/v1/pedidos`);
        if (!response.ok) throw new Error(`Status HTTP: ${response.status}`);

        const pedidos = await response.json();

        tbody.innerHTML = '';

        if (!pedidos || pedidos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--text-sub);">Nenhum pedido encontrado.</td></tr>';
            return;
        }

        pedidos.forEach(pedido => {
            const tr = document.createElement('tr');
            
            const id = pedido.id || '';
            const destinatario = pedido.nomeDestinatario || '-';
            const status = pedido.statusPedido || '-';
            const ocorrencia = pedido.statusUltimaOcorrencia || '-';

            tr.innerHTML = `
                <td><code>${id}</code></td>
                <td>${destinatario}</td>
                <td>${status}</td>
                <td>${ocorrencia}</td>
                <td>
                    <button type="button" class="btn btn-sm" onclick="selecionarPedido('${id}')">Selecionar</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

    } catch (error) {
        console.error('Erro ao buscar todos os pedidos:', error);
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--danger-text);">Erro ao carregar os pedidos do servidor.</td></tr>';
    }
}

// 5. Seleciona um pedido da listagem geral e preenche o formulário de encerramento
function selecionarPedido(id) {
    document.getElementById('inputBuscaId').value = id;
    buscarPedido();
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

async function carregarPedidosDoRoteiro(codigoRoteiro) {
    try {
        const response = await fetch(`${API_BASE_URL}/v1/pedidos/roteiro/${codigoRoteiro}`);
        const pedidos = await response.json();

        console.log(`Carregados ${pedidos.length} pedidos do roteiro ${codigoRoteiro}`);
        // Aqui você pode preencher uma tabela de pedidos para seleção em lote!
    } catch (error) {
        alert('Erro ao carregar os pedidos do roteiro.');
    }
}
