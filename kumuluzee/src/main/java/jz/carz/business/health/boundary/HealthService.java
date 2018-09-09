package jz.carz.business.health.boundary;

import javax.enterprise.context.RequestScoped;
import javax.transaction.TransactionScoped;

@RequestScoped
public class HealthService {

    public boolean isHealthy() {
        return true;
    }
}