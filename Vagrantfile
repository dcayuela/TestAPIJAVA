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
  config.vm.synced_folder ".", "/vagrant", type: "smb"

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
      app.vm.provider "hyperv" do |hv|
        hv.cpus = 4
        hv.memory = 4096
        hv.enable_virtualization_extensions = true
        hv.vmname = "UbuntuDockerRootless"
      end
  
      # 1- Provisioning Shell (bootstrap)
      unless SKIP_SHELL
	      app.vm.provision "shell", inline: <<-SHEEL
          sudo sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/g' /etc/ssh/sshd_config
          sudo systemctl restart sshd

          set -e

          echo "=== Mise à jour & dépendances ==="
          sudo apt-get update -y
          sudo apt-get install -y curl wget git sudo

          echo "=== Création utilisateur docker pour rootless ==="
          id -u dockeruser &>/dev/null || sudo adduser --disabled-password --gecos "" dockeruser
          sudo usermod -aG sudo dockeruser

          echo "=== Installer Docker rootless ==="
          su - dockeruser -c "curl -fsSL https://get.docker.com/rootless | sh"

          echo 'export PATH=/home/dockeruser/bin:$PATH' >> /home/dockeruser/.bashrc

          echo "=== Installer Docker Compose V2 ==="
          sudo curl -SL https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose
          sudo chmod +x /usr/local/bin/docker-compose

          echo "=== Vérification Docker rootless et Compose ==="
          su - dockeruser -c "docker --version"
          su - dockeruser -c "docker compose version"

          echo "=== Provisioning terminé ==="

        SHELL
      else
        puts "Shell provisioning skipped (SKIP_SHELL=true)"
      end

	end
	  
end