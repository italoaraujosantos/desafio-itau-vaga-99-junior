package br.dev.isac.transacaoapi.busisness.services;

import br.dev.isac.transacaoapi.controller.dtos.TransacaoRequestDTO;
import br.dev.isac.transacaoapi.infrastructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionarTrasacoes(TransacaoRequestDTO dto) {

        log.info("Iniciando o processamento de gravar transações");
        if(dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.info("Data e hora maiores que data e hora atuais");
            throw new UnprocessableEntity("Data e hora maiores que data e hora atuais");
        }

        if(dto.valor() < 0) {
            log.info("Valor não pode ser negativo");
            throw new UnprocessableEntity("Valor não pode ser negativo");
        }
        log.info("Transações adicionadas com sucesso");
        listaTransacoes.add(dto);
    }

    public void limparTransacoes() {
        log.info("Detelando transações com sucesso");
        listaTransacoes.clear();
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca) {
        log.info("Iniadas as buscas de transações por intervalor de tempo "+intervaloBusca+" segundo(s)");
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);
        log.info("Retorno de transações com sucesso");
        return listaTransacoes.stream().filter(transacao -> transacao.dataHora().isAfter(dataHoraIntervalo)).toList();
    }
}
