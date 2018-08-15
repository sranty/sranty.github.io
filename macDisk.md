
``` sh

/dev/disk0 (internal, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:      GUID_partition_scheme                        *500.3 GB   disk0
   1:                        EFI EFI                     209.7 MB   disk0s1
   2:                  Apple_HFS Macintosh HD            499.4 GB   disk0s2
   3:                 Apple_Boot Recovery HD             650.1 MB   disk0s3

/dev/disk1 (external, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:     FDisk_partition_scheme                        *2.0 TB     disk1
   1:               Windows_NTFS TOSHIBA EXT             2.0 TB     disk1s1

SSR-Mac@ssrmac.local [~]$ sudo diskutil mount /dev/disk1
Password:
Volume on disk1 failed to mount; if it has a partitioning scheme, use "diskutil mountDisk"
If the volume is damaged, try the "readOnly" option
If the volume is an APFS Volume, try the "diskutil apfs unlockVolume" verb
SSR-Mac@ssrmac.local [~]$ sudo diskutil mountDisk /dev/disk1
Volume(s) mounted successfully
SSR-Mac@ssrmac.local [~]$ diskutil list
/dev/disk0 (internal, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:      GUID_partition_scheme                        *500.3 GB   disk0
   1:                        EFI EFI                     209.7 MB   disk0s1
   2:                  Apple_HFS Macintosh HD            499.4 GB   disk0s2
   3:                 Apple_Boot Recovery HD             650.1 MB   disk0s3

/dev/disk1 (external, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:     FDisk_partition_scheme                        *2.0 TB     disk1
   1:               Windows_NTFS TOSHIBA EXT             2.0 TB     disk1s1

SSR-Mac@ssrmac.local [~]$ sudo diskutil mountDisk /dev/disk1



```