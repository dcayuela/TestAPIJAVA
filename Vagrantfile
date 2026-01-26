#  -*-  mode:  ruby -*-
# vi: set ft=ruby  :

# Flags via variables d'environnement
SKIP_SHELL   = ENV["SKIP_SHELL"] == "false"

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION)  do  |config|
  # Config Globale
  # Box Bento Ubuntu 22.04
  config.vm.box_download_insecure=true
  config.vm.boot_timeout = 1200
  config.vm.box  =  "bento/ubuntu-22.04"
  config.ssh.insert_key  =  false
  
  # Synced folder
  # Le dossier local . sera monté dans /vagrant dans la VM
  config.vm.synced_folder ".", "/vagrant", type: "virtualbox"

  # Ressources
  config.vm.provider  :virtualbox  do  |v|
  	v.memory  =  5120
  	v.linked_clone  =  true
  end

  # Config spécifique
  config.vm.define  "Docker"  do  |app|
      # Box Bento Ubuntu 22.04
      app.vm.box = "bento/ubuntu-22.04"
      app.vm.hostname = "ubuntu-docker-rootless"
	    # Réseau privé
      app.vm.network "private_network", ip: "192.168.50.10"
      # Ressources
      app.vm.provider "virtualbox" do |vb|
        vb.name = "ubuntu-22.04-docker-rootless"
        vb.cpus = 4
        vb.memory = 4096
      end
  
      # 1- Provisioning Shell (bootstrap)
      unless SKIP_SHELL
	      app.vm.provision "shell", privileged: true, inline: <<-SHELL
          sudo sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/g' /etc/ssh/sshd_config
          sudo systemctl restart sshd

          set -e

          echo "=== Mise à jour & dépendances ==="
          sudo apt-get update -y
          sudo apt-get install -y \
            ca-certificates \
            curl \
            wget \
            git \
            sudo \
            uidmap \
            dbus-user-session \
            slirp4netns \
            fuse-overlayfs

            echo "🐳 Installation Docker (rootless ready)"
            curl -fsSL https://get.docker.com | sh

            echo "📦 Installation Docker Compose v2 (plugin)"
            apt-get install -y docker-compose-plugin

        SHELL
      else
        puts "Shell provisioning skipped (SKIP_SHELL=true)"
      end       
      
      unless SKIP_SHELL
	      app.vm.provision "shell", privileged: false, inline: <<-SHELL     

            set -e

            echo "🔑 Enable linger"
            loginctl enable-linger vagrant

            echo "🐳 Installation Docker rootless (no cgroup)"
            dockerd-rootless-setuptool.sh install --skip-iptables

            echo "🔧 Désactivation explicite des cgroups"
            mkdir -p ~/.config/docker
            cat <<EOF > ~/.config/docker/daemon.json
{
  "exec-opts": ["native.cgroupdriver=none"]
}
EOF

            echo "🔐 Variables d'environnement"
            echo 'export DOCKER_HOST=unix:///run/user/1000/docker.sock' >> ~/.bashrc

            echo "▶️ Démarrage Docker rootless"
            systemctl --user daemon-reexec
            systemctl --user restart docker

            echo "✅ Docker rootless prêt (sans reboot)"

        SHELL
      else
        puts "Shell provisioning skipped (SKIP_SHELL=true)"
      end

	end
	  
end