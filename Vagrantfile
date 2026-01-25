#  -*-  mode:  ruby -*-
# vi: set ft=ruby  :

# Flags via variables d'environnement
SKIP_SHELL   = ENV["SKIP_SHELL"] == "false"
REPROVISION  = ENV["REPROVISION"] == "true"

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
  config.vm.synced_folder  ".",  "/vagrant", type: "virtualbox"

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
        vb.cpus = 4
        vb.memory = 4096
      end
  
      # 1- Provisioning Shell (bootstrap)
      unless SKIP_SHELL
	      app.vm.provision "shell", inline: <<-SHEEL
          sudo sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/g' /etc/ssh/sshd_config
          sudo systemctl restart sshd
        SHELL
      else
        puts "Shell provisioning skipped (SKIP_SHELL=true)"
      end

      # Provisioning Ansible
      app.vm.provision "ansible" do |ansible|
        ansible.playbook = "ansible/playbook.yml"
        ansible.compatibility_mode = "2.0"
        ansible.run = REPROVISION ? "always" : "once"
      end

	end
	  
end