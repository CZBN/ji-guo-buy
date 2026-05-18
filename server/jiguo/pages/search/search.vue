<template>
	<view class="search-page">
		<view class="search-box">
			<input v-model="key" confirm-type="search" placeholder="手机/数码/关键字" @confirm="doSearch" />
			<button size="mini" @tap="doSearch">搜索</button>
		</view>
		<view class="result-list">
			<view class="result-item" v-for="item in results" :key="item.id" @tap="gotoDetail(item)">
				<image :src="buildImageUrl(item.image)" mode="aspectFill"></image>
				<view class="info">
					<view class="name">{{item.name}}</view>
					<view class="title">{{item.title}}</view>
					<view class="price">￥{{item.price || '暂无'}}</view>
				</view>
			</view>
		</view>
		<view class="empty" v-if="searched && results.length === 0">没有找到相关商品</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				key: "",
				results: [],
				imageUrl: "",
				searched: false
			}
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
		},
		onNavigationBarSearchInputChanged(event) {
			this.key = event.text;
		},
		onNavigationBarButtonTap(event) {
			if (event.index == 0) {
				this.doSearch();
			} else {
				uni.switchTab({ url: "/pages/home/home" });
			}
		},
		methods: {
			buildImageUrl(imagePath) {
				if (!imagePath) return "";
				return this.imageUrl + "/" + imagePath;
			},
			doSearch() {
				const key = this.key.trim();
				if (!key) {
					uni.showToast({ title: "请输入关键词", icon: "none" });
					return;
				}
				uni.request({
					url: "/api/coolitem/searchByKey",
					data: { key, type: "title", pno: 1, size: 20 },
					success: (resp) => {
						this.searched = true;
						this.results = resp.data.code === 0 && resp.data.data ? (resp.data.data.list || []) : [];
					},
					fail: () => {
						uni.showToast({ title: "搜索失败", icon: "error" });
					}
				})
			},
			gotoDetail(item) {
				uni.navigateTo({ url: "/pages/cooldetail/cooldetail?id=" + item.id });
			}
		}
	}
</script>

<style scoped>
	.search-page {
		min-height: 100vh;
		background: #f6f7f8;
		padding: 24rpx;
	}

	.search-box {
		display: flex;
		gap: 16rpx;
		align-items: center;
		margin-bottom: 24rpx;
	}

	.search-box input {
		flex: 1;
		height: 72rpx;
		padding: 0 24rpx;
		background: #fff;
		border-radius: 12rpx;
	}

	.result-item {
		display: flex;
		background: #fff;
		margin-bottom: 18rpx;
		border-radius: 12rpx;
		overflow: hidden;
	}

	.result-item image {
		width: 190rpx;
		height: 160rpx;
		background: #eee;
	}

	.info {
		flex: 1;
		padding: 18rpx;
	}

	.name {
		font-size: 30rpx;
		font-weight: 700;
		color: #222;
	}

	.title {
		margin-top: 10rpx;
		font-size: 25rpx;
		line-height: 1.4;
		color: #666;
	}

	.price {
		margin-top: 14rpx;
		color: #e53935;
		font-size: 28rpx;
		font-weight: 700;
	}

	.empty {
		margin-top: 80rpx;
		text-align: center;
		color: #888;
	}
</style>
