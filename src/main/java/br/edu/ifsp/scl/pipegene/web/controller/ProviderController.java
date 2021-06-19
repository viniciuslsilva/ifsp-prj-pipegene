package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.configuration.security.AuthenticationFacade;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.account.ApplicationUser;
import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionTransaction;
import br.edu.ifsp.scl.pipegene.usecases.provider.ProviderService;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderExecutionResultRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.response.ProviderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/providers")
public class ProviderController {

    private final ExecutionTransaction executionTransaction;
    private final ProviderService providerService;
    private final AuthenticationFacade authenticationFacade;

    public ProviderController(ExecutionTransaction executionTransaction, ProviderService providerService, AuthenticationFacade authenticationFacade) {
        this.executionTransaction = executionTransaction;
        this.providerService = providerService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping
    public ResponseEntity<ProviderResponse> addNewProvider(@RequestBody @Valid ProviderRequest providerRequest) {
        Provider provider = providerService.createNewProvider(providerRequest);

        return ResponseEntity.ok(ProviderResponse.createFromProvider(provider));
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponse>> listAllProviders() {
        List<Provider> providers = providerService.listAllProviders();

        return ResponseEntity.ok(
                providers.stream()
                        .map(ProviderResponse::createFromProvider)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/{providerId}/executions/{executionId}/steps/{stepId}")
    public ResponseEntity<?> notifyExecutionResult(
            @PathVariable UUID providerId,
            @PathVariable UUID executionId,
            @PathVariable UUID stepId,
            @RequestBody ProviderExecutionResultRequest providerExecutionResultRequest
    ) {

        executionTransaction.validateNotificationFromProvider(providerId, executionId, stepId);
        executionTransaction.processAsyncExecutionResult(providerId, executionId, stepId, providerExecutionResultRequest);

        return ResponseEntity.accepted().build();
    }
}
