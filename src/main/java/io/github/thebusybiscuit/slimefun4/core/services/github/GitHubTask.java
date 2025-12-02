package io.github.thebusybiscuit.slimefun4.core.services.github;

import java.net.http.HttpClient;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

/**
 * This {@link GitHubTask} represents a {@link Runnable} that is run every X minutes.
 * It retrieves every {@link Contributor} of this project from GitHub.
 * 
 * @author TheBusyBiscuit
 * 
 * @see GitHubService
 * @see Contributor
 *
 */
class GitHubTask implements Runnable {

    private final GitHubService gitHubService;

    GitHubTask(@Nonnull GitHubService github) {
        gitHubService = github;
    }

    @Override
    public void run() {

        if (Bukkit.isPrimaryThread()) {
            Slimefun.logger().log(Level.SEVERE, "The contributors task may never run on the main Thread!");
            return;
        }

        connectAndCache();
        gitHubService.saveCache();
    }

    private void connectAndCache() {
        gitHubService.getConnectors().forEach(GitHubConnector::download);
    }
}
