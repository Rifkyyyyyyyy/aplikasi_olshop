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

    /**
     * Metode untuk memperbarui saldo pengguna.
     * 
     * @param data data saldo yang akan diperbarui
     * @return CompletableFuture yang berisi hasil dari operasi pembaruan saldo
     */
    public CompletableFuture<Boolean> updateBalance(BalanceModel data) {
        return updateBalanceUsecase.call(data).thenApply((result) -> {
            if (result) {
                System.out.println("Saldo berhasil dimasukkan: " + data.toString());
            } else {
                System.out.println("Gagal memasukkan saldo");
            }
            return result;
        });
    }

    /**
     * Metode untuk mengambil saldo pengguna berdasarkan ID.
     * 
     * @param id ID pengguna yang saldo-nya ingin diambil
     * @return CompletableFuture yang berisi objek BalanceModel yang merepresentasikan saldo pengguna
     */
    public CompletableFuture<BalanceModel> getBalance(String id) {
        return getBalans.call(id).thenApply((result) -> {
            if (result != null) {
                System.out.println("Saldo berhasil diambil untuk ID: " + id + " Data: " + result.toString());
            } else {
                System.out.println("Gagal mengambil saldo untuk ID: " + id);
            }
            return result;
        });
    }
}
