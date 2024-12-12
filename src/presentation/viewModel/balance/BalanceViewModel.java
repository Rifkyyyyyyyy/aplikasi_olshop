package presentation.viewModel.balance;

import java.util.concurrent.CompletableFuture;

import domain.model.balance.BalanceModel;
import domain.usecase.balance.GetBalanceByIdUsecase;
import domain.usecase.balance.UpdateBalanceUsecase;

public class BalanceViewModel {
    private final UpdateBalanceUsecase updateBalanceUsecase;
    private final GetBalanceByIdUsecase getBalans;

    public BalanceViewModel(UpdateBalanceUsecase updateBalanceUsecase, GetBalanceByIdUsecase getBalanceById) {
        this.updateBalanceUsecase = updateBalanceUsecase;
        this.getBalans = getBalanceById;
    }

     public  CompletableFuture<Boolean> insertBalance (BalanceModel data) {
        return updateBalanceUsecase.call(data).thenApply((result) -> {
            if(result) {
                System.out.println("Balance inserted successfully : " + data.toString());
            } else {
                System.out.println("Failed to insert balance");
            }
            return result;
        });
     }

     public  CompletableFuture<BalanceModel> getBalance(String id) {
        return  getBalans.call(id).thenApply((result) -> {
            if(result != null) {
                System.out.println("Balance fetched successfully for : " + id + "Data" + result.toString());
            } else {
                System.out.println("Failed to fetch balance for : " + id);
            }
            return  result;
        });
     }

}
