package br.dev.isac.transacaoapi.busisness.services;

import br.dev.isac.transacaoapi.controller.dtos.EstatisticasResponseDTO;
import br.dev.isac.transacaoapi.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransaccoes(Integer intervalorBusca) {
        log.info("Iniciada a busca de estatisticas de transações por intervalor de tempo "+intervalorBusca+" segundo(s)");
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervalorBusca);

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();
        if(transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L, 0.0,0.0,0.0,0.0);
        }
        log.info("Estatisticas retornadas com sucesso");
        return new EstatisticasResponseDTO(
               estatisticasTransacoes.getCount(),
               estatisticasTransacoes.getSum(),
               estatisticasTransacoes.getAverage(),
               estatisticasTransacoes.getMin(),
               estatisticasTransacoes.getMax()
       );
    }

}
