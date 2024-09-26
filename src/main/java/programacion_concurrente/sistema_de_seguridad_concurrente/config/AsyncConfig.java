package programacion_concurrente.sistema_de_seguridad_concurrente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "AsyctaskExecutor")
    public Executor AsyctaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // Ajusta el tamaño del pool de hilos según sea necesario
        executor.setMaxPoolSize(8); // Ajusta el tamaño máximo del pool de hilos según sea necesario
        executor.setQueueCapacity(1000); // Ajusta la capacidad de la cola según sea necesario
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}
