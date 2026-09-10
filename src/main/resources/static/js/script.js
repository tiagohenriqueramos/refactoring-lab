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
