/*
 * System
 *
 * Created by Ed Gamble <ed@breadwallet.com> on 1/22/18.
 * Copyright (c) 2018-2019 Breadwinner AG.  All right reserved.
 *
 * See the LICENSE file at the project root for license information.
 * See the CONTRIBUTORS file at the project root for a list of contributors.
 */
package com.breadwallet.crypto;

import com.breadwallet.crypto.blockchaindb.BlockchainDb;
import com.breadwallet.crypto.events.system.SystemListener;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public interface System {

    static System create(ScheduledExecutorService executor, SystemListener listener, Account account, boolean isMainnet, String path, BlockchainDb query) {
        return CryptoApi.getProvider().systemProvider().create(executor, listener, account, isMainnet,path, query);
    }

    void configure(List<com.breadwallet.crypto.blockchaindb.models.bdb.Currency> appCurrencies);

    /**
     * Create a wallet manager for `network` using `mode.
     *
     * @param network the wallet manager's network
     * @param mode the wallet manager mode to use
     * @param addressScheme the address scheme to use
     * @param currencies the currencies to 'register'.  A wallet will be created for each one.  It
     *                   is safe to pass currencies not in `network` as they will be filtered (but bad form
     *                   to do so).
     */
    void createWalletManager(Network network,
                             WalletManagerMode mode,
                             AddressScheme addressScheme,
                             Set<Currency> currencies);

    void stop();

    void subscribe(String subscriptionToken);

    /**
     * Set the network reachable flag for all managers.
     *
     * Setting or clearing this flag will NOT result in a connect/disconnect attempt by a {@link WalletManager}.
     * Callers must use the {@link WalletManager#connect()}/{@link WalletManager#disconnect()} methods to
     * change a WalletManager's connectivity state. Instead, WalletManagers MAY consult this flag when performing
     * network operations to determine viability.
     */
    void setNetworkReachable(boolean isNetworkReachable);

    Account getAccount();

    String getPath();

    List<? extends Network> getNetworks();

    List<? extends WalletManager> getWalletManagers();

    List<? extends Wallet> getWallets();

    AddressScheme getDefaultAddressScheme(Network network);

    List<AddressScheme> getSupportedAddressSchemes(Network network);

    boolean supportsAddressScheme(Network network, AddressScheme addressScheme);

    WalletManagerMode getDefaultWalletManagerMode(Network network);

    List<WalletManagerMode> getSupportedWalletManagerModes(Network network);

    boolean supportsWalletManagerModes(Network network, WalletManagerMode mode);
}
